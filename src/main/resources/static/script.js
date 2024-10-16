document.addEventListener("DOMContentLoaded", function () {
    const buttons = document.querySelectorAll('.category-btn');
    const mainImage = document.getElementById('mainImage');

    const initialButton = buttons[0];
    mainImage.src = initialButton.getAttribute('data-image');
    initialButton.classList.add('active');

    buttons.forEach(button => {
        button.addEventListener('click', function () {
            mainImage.src = button.getAttribute('data-image');

            buttons.forEach(btn => btn.classList.remove('active'));
            button.classList.add('active');
        });
    });
});