var commonLib = commonLib ?? {};
commonLib.fileManager = {
    //upload() 함수로 파일 업로드 처리 기능!
    upload(files, gid, location, single, imageOnly) {
        try {
            if (!files || files.length === 0) {
                throw new Error("파일을 선택하세요.");
            }

            if (imageOnly) { // 이미지만 업로드 하는 경우, for문으로 검증하자!
                for (const file of files) {
                    if (file.type.indexOf("image/") === -1) { // 이미지가 아닌 파일인 경우
                        throw new Error("이미지 형식이 아닙니다. 이미지 형식만 올려주시길 바랍니다.");

                    }
                }

            }

        } catch (err) {
            alert(err.message);
            console.error(err);
        }
    }

};


window.addEventListener("DOMContentLoaded", function() {

    const fileUploads = document.getElementsByClassName("file-upload");

    const fileEl = document.createElement("input");
    fileEl.type = 'file';

    for (const el of fileUploads) {
        // 디자인 패턴 - 옵저버 패턴
        el.addEventListener("click", function() {
            const {gid, location, single, imageOnly} = this.dataset;

            fileEl.gid = gid;
            fileEl.location = location;
            fileEl.imageOnly = imageOnly === 'true';
            fileEl.single = single === 'true';
            fileEl.multiple = !fileEl.single; // false - 단일 파일 선택, true - 여러파일 선택 가능

            fileEl.click();
        });
    }

    // File 선택시 - change Event 발생
    fileEl.addEventListener("change", function(e) {
        // 선택된 객체 e.currentTarget & e.target 의 차이
        const files = e.currentTarget.files;
        const {gid, location, single, imageOnly} = fileEl;

        const { fileManager } = commonLib;
        fileManager.upload(files, gid, location, single, imageOnly);
    });
});