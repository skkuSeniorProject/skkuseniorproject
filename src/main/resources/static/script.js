document.addEventListener("DOMContentLoaded", function () {
    const buildingBoxes = document.querySelectorAll('.building-box');

    buildingBoxes.forEach(box => {
        box.addEventListener('click', () => {
            const buildingNumber = box.getAttribute('data-building');
            const statusElements = box.querySelectorAll('.building-status-child');
            if (statusElements.length < 2) {
                console.error('Status elements not found');
                return;
            }
            fetchBuildingInfo(buildingNumber, statusElements);
        });
    });

    function fetchBuildingInfo(buildingCode, statusElements) {
        // AJAX 요청 보내기 (GET 방식)
        fetch(`/building/congestion?code=${buildingCode}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // 서버로부터 받은 데이터 처리
                const { remain_seat, congestion_state } = data;
                statusElements[0].textContent = `인원: ${remain_seat}`;
                statusElements[1].textContent = `상태: ${congestion_state}`;
            })
            .catch(error => {
                console.error('Error fetching building info:', error);
                statusElements[0].textContent = '인원: 정보 오류';
                statusElements[1].textContent = '상태: 정보 오류';
            });
    }

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