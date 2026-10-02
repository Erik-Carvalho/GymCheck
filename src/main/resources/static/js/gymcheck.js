if ('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
        navigator.serviceWorker.register('/sw.js').catch((error) => {
            console.error('Falha ao registrar o Service Worker:', error);
        });
    });
}

document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('[data-toast]').forEach((toast, index) => {
        toast.style.position = 'fixed';
        toast.style.zIndex = '100';
        toast.style.left = '1rem';
        toast.style.right = '1rem';
        toast.style.bottom = `${1 + index * 4.5}rem`;
        toast.style.maxWidth = '28rem';
        toast.style.marginInline = 'auto';
        toast.style.boxShadow = '0 16px 40px rgb(2 6 23 / 0.45)';
        toast.setAttribute('aria-live', toast.getAttribute('role') === 'alert' ? 'assertive' : 'polite');
        toast.setAttribute('aria-atomic', 'true');

        window.setTimeout(() => {
            toast.style.opacity = '0';
            toast.style.transform = 'translateY(0.5rem)';
            window.setTimeout(() => toast.remove(), 300);
        }, 6000);
    });
});