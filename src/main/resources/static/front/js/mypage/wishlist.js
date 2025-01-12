document.addEventListener("DOMContentLoaded", function () {
    const recommendButtons = document.querySelectorAll(".recommend-btn");

    recommendButtons.forEach((button) => {
        button.addEventListener("click", function () {
            const pokemonId = this.dataset.id;

            fetch(`/mypage/recommend/${pokemonId}`, {
                method: "POST"
            })
            .then((response) => response.text())
            .then((message) => {
                alert(message); // 추천등록완료! 메시지 출력
            })
            .catch((error) => {
                console.error("Error:", error);
            });
        });
    });
});