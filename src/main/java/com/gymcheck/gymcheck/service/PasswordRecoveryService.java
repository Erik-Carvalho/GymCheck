package com.gymcheck.gymcheck.service;

import com.gymcheck.gymcheck.model.PasswordResetToken;
import com.gymcheck.gymcheck.model.Usuario;
import com.gymcheck.gymcheck.repository.PasswordResetTokenRepository;
import com.gymcheck.gymcheck.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Locale;

@Service
public class PasswordRecoveryService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordRecoveryService.class);
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Duration tokenValidity = Duration.ofMinutes(30);

    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final Clock clock;
    private final String baseUrl;
    private final String fromAddress;

    public PasswordRecoveryService(UsuarioRepository usuarioRepository,
                                   PasswordResetTokenRepository tokenRepository,
                                   PasswordEncoder passwordEncoder,
                                   JavaMailSender mailSender,
                                   Clock clock,
                                   @Value("${gymcheck.base-url}") String baseUrl,
                                   @Value("${gymcheck.mail.from}") String fromAddress) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.clock = clock;
        this.baseUrl = baseUrl.replaceAll("/+$", "");
        this.fromAddress = fromAddress;
    }

    @Transactional
    public void solicitarRecuperacao(String email) {
        if (email == null || email.isBlank()) return;
        Usuario usuario = usuarioRepository.findByEmailIgnoreCase(email.trim().toLowerCase(Locale.ROOT))
                .orElse(null);
        if (usuario == null) return;

        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        String tokenBruto = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);

        PasswordResetToken token = tokenRepository.findByUsuarioId(usuario.getId())
                .orElseGet(PasswordResetToken::new);
        token.setUsuario(usuario);
        token.setTokenHash(hashToken(tokenBruto));
        token.setExpiresAt(Instant.now(clock).plus(tokenValidity));
        tokenRepository.save(token);

        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setFrom(fromAddress);
        mensagem.setTo(usuario.getEmail());
        mensagem.setSubject("Redefinição de senha do GymCheck");
        mensagem.setText("Recebemos uma solicitação para redefinir sua senha. Acesse o link abaixo em até 30 minutos:\n\n"
                + baseUrl + "/redefinir-senha?token=" + tokenBruto
                + "\n\nSe você não solicitou essa alteração, ignore este e-mail.");

        try {
            mailSender.send(mensagem);
        } catch (MailException exception) {
            tokenRepository.delete(token);
            logger.warn("Não foi possível enviar recuperação de senha para usuarioId={}", usuario.getId(), exception);
        }
    }

    @Transactional(readOnly = true)
    public boolean tokenValido(String tokenBruto) {
        if (tokenBruto == null || tokenBruto.isBlank()) return false;
        return tokenRepository.findByTokenHash(hashToken(tokenBruto))
                .map(token -> token.getExpiresAt().isAfter(Instant.now(clock)))
                .orElse(false);
    }

    @Transactional
    public boolean redefinirSenha(String tokenBruto, String novaSenha) {
        if (tokenBruto == null || tokenBruto.isBlank()) return false;
        PasswordResetToken token = tokenRepository.findByTokenHash(hashToken(tokenBruto)).orElse(null);
        if (token == null || !token.getExpiresAt().isAfter(Instant.now(clock))) {
            if (token != null) tokenRepository.delete(token);
            return false;
        }

        Usuario usuario = token.getUsuario();
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuarioRepository.save(usuario);
        tokenRepository.delete(token);
        return true;
    }

    private String hashToken(String tokenBruto) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(tokenBruto.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 não está disponível.", exception);
        }
    }
}