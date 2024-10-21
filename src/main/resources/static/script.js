document.addEventListener("DOMContentLoaded", function () {
    const buildingBoxes = document.querySelectorAll('.building-box');
    const resultElement = document.getElementById('result');

    buildingBoxes.forEach(box => {
        box.addEventListener('click', () => {
            const buildingNumber = box.getAttribute('data-building');
            fetchBuildingInfo(buildingNumber);
        });
    });
    function fetchBuildingInfo(buildingCode) {
        // AJAX 요청 보내기 (GET 방식)
        fetch(`/building/congestion?code=${buildingCode}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
            }
        })
            .then(response => response.json())
            .then(data => {
                // 서버로부터 받은 데이터 처리
                const { remain_seat, congestion_state } = data; // JSON 필드명이 snake_case로 매핑됨
                resultElement.textContent = `남은 좌석: ${remain_seat}, 상태: ${congestion_state}`;
            })
            .catch(error => {
                console.error('Error fetching building info:', error);
                resultElement.textContent = '정보를 가져오는 데 오류가 발생했습니다.';
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