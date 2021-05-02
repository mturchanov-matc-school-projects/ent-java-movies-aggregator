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
    let topSearchesBtn = document.querySelector('#topSearchesBtn');
    let revShowBtn = document.querySelector('#reviewSourcesBtn');

    btnCheckAll.addEventListener("click", toggle);
    btnResetAll.addEventListener("click", reset);
    topSearchesBtn.addEventListener("click", showHideTopSearches);
    revShowBtn.addEventListener("click", showRevs);

}


const showRevs = () => {
    let mainFunctionality = document.querySelector('div#mainFunc');
    let revShowBtn = document.querySelector('#reviewSourcesBtn');
    let reviewSources = document.querySelector('div#reviewSources');

    if (mainFunctionality.classList.contains('col-md-12')) {
        revShowBtn.innerHTML = "Hide review sources"
        mainFunctionality.classList.remove('col-md-12');
        mainFunctionality.classList.add('col-md-4');
        reviewSources.classList.add('active')
    } else if (mainFunctionality.classList.contains('col-md-4')){
        reviewSources.classList.remove('active')
        revShowBtn.innerHTML = "Show review sources"
        mainFunctionality.classList.remove('col-md-4');
        mainFunctionality.classList.add('col-md-12');
    }
}

const showHideTopSearches = ()  => {
    let topSearches = document.querySelector('table#topSearches');
    let topSearchesBtn = document.querySelector('#topSearchesBtn');

    if (!topSearches.classList.contains("active")) {
        topSearchesBtn.innerHTML = "Hide top searches"
        topSearches.classList.add('active');
    } else {
        topSearchesBtn.innerHTML = "Show top searches"
        topSearches.classList.remove('active');
    }
}