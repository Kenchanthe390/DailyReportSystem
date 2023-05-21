/** 削除ボタンを押した時の処理 */
function clickBtnDelete() {

    // 確認ダイアログを表示
    if (window.confirm(`削除してよろしいでしょうか？（削除後は復旧することができません）`)) {
        // OKが押されたら処理を実行
        return true;
    } else {
        return false;
    }
}

// 削除ボタンに関数を割り当てる
document.getElementById("deleteRun").onclick = clickBtnDelete;