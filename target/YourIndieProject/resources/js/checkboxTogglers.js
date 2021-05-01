const toggle = ()  => {
    let checkboxes = document.getElementsByName('reviewsSources');
    for(let i = 0, len = checkboxes.length; i < len;i++) {
        checkboxes[i].checked = true;
    }
}

const reset = () => {
    let checkboxes = document.getElementsByName('reviewsSources');
    for(let i = 0, len = checkboxes.length; i < len;i++) {
        checkboxes[i].checked = false;
    }
}

window.onload = () => {
    let btnCheckAll = document.querySelector("#checkAllBtn");
    let btnResetAll = document.querySelector("#resetAllBtn");
    btnCheckAll.addEventListener("click", toggle);
    btnResetAll.addEventListener("click", reset);
}