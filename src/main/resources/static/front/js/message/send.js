// 파일을 올릴 경우 후속처리를 위한 JS
window.addEventListener("DOMContentLoaded", function() {
    const { loadEditor } = commonLib;

    loadEditor("content", 350)
        .then((editor) => {
            window.editor = editor; // 전역 변수로 등록, then 구간 외부에서도 접근 가능하게 처리
        });
});

/**
* 파일 업로드 완료 후 성공 후속 처리
*
*/
function callbackFileUpload(files) {
    console.log(files);
}