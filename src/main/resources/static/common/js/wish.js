window.addEventListener("DOMContentLoaded", function() {
    // 찜하기 버튼 로직
    const wishButtons = document.getElementsByClassName("wish-btn");
    for (const el of wishButtons) {
        el.addEventListener("click", function() {
            if (this.classList.contains("guest")) { // 미로그인 상태
                alert("로그인이 필요한 서비스 입니다.");
                const { pathname, search } = location;
                const redirectUrl = search ? pathname + search : pathname;
                location.href = commonLib.url(`/member/login?redirectUrl=${redirectUrl}`);
                return;
            }

            let apiUrl = commonLib.url("/api/wish/");
            const classList = this.classList;
            if (classList.contains("on")) { // 찜 제거
                apiUrl += "remove";
            } else { // 찜 추가
                apiUrl += "add";
            }

            const { seq, type } = this.dataset;

            apiUrl += `?seq=${seq}&type=${type}`;
            const { ajaxLoad } = commonLib;

            const icon = this.querySelector("i");

            (async () => {
                try {
                    await ajaxLoad(apiUrl);

                    if (classList.contains("on")) { // 찜 제거
                        icon.className = "xi-heart-o";
                    } else { // 찜 추가
                        icon.className = "xi-heart";
                    }

                    classList.toggle("on");

                } catch (err) {
                    alert(err.message);
                }
            })();
        });
    }

    // 추천 버튼 로직
    const recommendButtons = document.getElementsByClassName("recommend-btn");
    for (const button of recommendButtons) {
        button.addEventListener("click", function() {
            const seq = this.dataset.seq;

            fetch(`/pokemon/recommend/${seq}`, {
                method: "POST"
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error("추천 등록에 실패했습니다.");
                }
                alert("추천이 등록되었습니다!");
                location.reload(); // 추천 수 갱신
            })
            .catch(err => alert(err.message));
        });
    }
});
