if ('serviceWorker' in navigator) {
    window.addEventListener('load', () => {
        navigator.serviceWorker.register('/sw.js').catch((error) => {
            console.error('Falha ao registrar o Service Worker:', error);
        });
    });
}

document.addEventListener('DOMContentLoaded', () => {
    const loadingSpinnerStyles = document.createElement('style');
    loadingSpinnerStyles.textContent = '@keyframes gymcheck-button-spin { to { transform: rotate(360deg); } } .gymcheck-loading-spinner { display: inline-block; width: 1rem; height: 1rem; border: 2px solid currentColor; border-right-color: transparent; border-radius: 50%; animation: gymcheck-button-spin 0.7s linear infinite; vertical-align: middle; margin-right: 0.5rem; } @media (prefers-reduced-motion: reduce) { .gymcheck-loading-spinner { animation-duration: 1.5s; } }';
    document.head.append(loadingSpinnerStyles);

    const setButtonLoading = (button, label) => {
        if (button.dataset.originalContent === undefined) {
            button.dataset.originalContent = button.innerHTML;
        }

        const spinner = document.createElement('span');
        spinner.className = 'gymcheck-loading-spinner';
        spinner.setAttribute('aria-hidden', 'true');

        const loadingText = document.createElement('span');
        loadingText.textContent = label;
        button.replaceChildren(spinner, loadingText);
        button.disabled = true;
        button.setAttribute('aria-busy', 'true');
    };

    const resetButtonLoading = (button) => {
        button.disabled = false;
        button.removeAttribute('aria-busy');
        if (button.dataset.originalContent !== undefined) {
            button.innerHTML = button.dataset.originalContent;
            delete button.dataset.originalContent;
        }
    };

    document.querySelectorAll('[data-loading-form]').forEach((form) => {
        form.addEventListener('submit', (event) => {
            const button = event.submitter?.matches('[data-loading-submit]')
                ? event.submitter
                : form.querySelector('[data-loading-submit]');
            if (!button) return;
            setButtonLoading(button, button.dataset.loadingText || 'Salvando...');
        });
    });

    window.addEventListener('pageshow', () => {
        document.querySelectorAll('[data-loading-submit][aria-busy="true"]').forEach(resetButtonLoading);
    });

    document.querySelectorAll('[data-workout-observation]').forEach((textarea) => {
        const section = textarea.closest('[data-observation-url]');
        const saveButton = section?.querySelector('[data-save-observation]');
        const status = section?.querySelector('[data-observation-status]');
        const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
        const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
        let savedText = textarea.value;
        let saving = false;

        if (!section || !saveButton || !status) return;

        const saveObservation = async () => {
            if (saving || textarea.value === savedText) return;

            const textToSave = textarea.value;
            const headers = { 'Content-Type': 'application/json' };
            if (csrfToken && csrfHeader) headers[csrfHeader] = csrfToken;

            saving = true;
            setButtonLoading(saveButton, 'Salvando...');
            status.textContent = 'Salvando...';
            let saveSucceeded = false;

            try {
                const response = await fetch(section.dataset.observationUrl, {
                    method: 'PATCH',
                    headers,
                    body: JSON.stringify({ observacao: textToSave })
                });

                if (!response.ok) throw new Error('Não foi possível salvar as observações.');

                savedText = textToSave;
                saveSucceeded = true;
                status.textContent = 'Salvo';
            } catch (error) {
                status.textContent = error.message;
            } finally {
                saving = false;
                resetButtonLoading(saveButton);
                if (saveSucceeded && textarea.value !== savedText && document.activeElement !== textarea) {
                    saveObservation();
                }
            }
        };

        textarea.addEventListener('blur', saveObservation);
        saveButton.addEventListener('click', saveObservation);
    });

    const editExerciseModal = document.querySelector('[data-edit-exercise-modal]');
    if (editExerciseModal) {
        const editForm = editExerciseModal.querySelector('[data-edit-exercise-form]');
        const seriesInput = editForm.querySelector('[name="series"]');
        const repetitionsInput = editForm.querySelector('[name="repeticoes"]');
        const weightInput = editForm.querySelector('[name="peso"]');
        const status = editForm.querySelector('[data-edit-exercise-status]');
        const submitButton = editForm.querySelector('[data-update-exercise]');
        let activeEditButton = null;
        let activeItemCard = null;
        let updating = false;

        const closeEditModal = () => editExerciseModal.classList.add('hidden');

        document.querySelectorAll('[data-edit-item]').forEach((button) => {
            button.addEventListener('click', () => {
                if (updating) return;
                activeEditButton = button;
                activeItemCard = button.closest('[data-workout-item]');
                seriesInput.value = button.dataset.series;
                repetitionsInput.value = button.dataset.repeticoes;
                weightInput.value = button.dataset.peso;
                status.textContent = '';
                editExerciseModal.classList.remove('hidden');
                seriesInput.focus();
            });
        });

        editExerciseModal.querySelectorAll('[data-close-edit-exercise]').forEach((button) => {
            button.addEventListener('click', () => {
                if (!updating) closeEditModal();
            });
        });

        editForm.addEventListener('submit', async (event) => {
            event.preventDefault();
            if (!activeEditButton || !activeItemCard) return;

            const editedButton = activeEditButton;
            const editedItemCard = activeItemCard;
            const itemId = editedButton.dataset.itemId;
            const payload = {
                series: Number(seriesInput.value),
                repeticoes: Number(repetitionsInput.value),
                peso: Number(weightInput.value)
            };
            const headers = { 'Content-Type': 'application/json' };
            const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
            if (csrfToken && csrfHeader) headers[csrfHeader] = csrfToken;

            updating = true;
            setButtonLoading(submitButton, 'Salvando...');
            status.textContent = 'Salvando...';

            try {
                const response = await fetch(`/exercicios/${itemId}`, {
                    method: 'PUT',
                    headers,
                    body: JSON.stringify(payload)
                });
                if (!response.ok) throw new Error('Não foi possível atualizar o exercício.');

                const updated = await response.json();
                editedItemCard.querySelector('[data-item-series]').textContent = updated.series;
                editedItemCard.querySelector('[data-item-repeticoes]').textContent = updated.repeticoes;
                editedItemCard.querySelector('[data-item-peso]').textContent = updated.peso;
                editedButton.dataset.series = updated.series;
                editedButton.dataset.repeticoes = updated.repeticoes;
                editedButton.dataset.peso = updated.peso;
                closeEditModal();
            } catch (error) {
                status.textContent = error.message;
            } finally {
                updating = false;
                resetButtonLoading(submitButton);
            }
        });

        document.addEventListener('keydown', (event) => {
            if (event.key === 'Escape' && !updating && !editExerciseModal.classList.contains('hidden')) {
                closeEditModal();
            }
        });
    }

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