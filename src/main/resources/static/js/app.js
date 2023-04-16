const API_URL = '/api';
const messageIfDeleteAction = "Данные будут удалены без возможности восстановления. Точно удалить?";

(() => {
    'use strict'
    const forms = document.querySelectorAll('.needs-validation')
    Array.from(forms).forEach(form => {
        form.addEventListener('submit', event => {
            if (!form.checkValidity()) {
                event.preventDefault()
                event.stopPropagation()
            }
            form.classList.add('was-validated')
        }, false)
    })
})()

window.onload = () => {
    addListeners('input[name^=topic]', 'change', moveDeleteTopics, 'value');
    addListeners('span[id^=topic-delete-]', 'click', deleteTopic, 'id');
    addListeners('button[name^=topic-message-id-]', 'click', deleteTopicMessage, 'name');
    addListeners('button[name^=topic-message-text-]', 'click', editTopicMessage, 'name');
    addListeners('span[id^=topic-edit-]', 'click', topicEdit, 'id', 'topic');
    addListeners('span[class^=section-edit-]', 'click', sectionCatSubCatEdit, 'class', 'sections', 'section');
    addListeners('span[class^=category-edit-]', 'click', sectionCatSubCatEdit, 'class', 'cat', 'category');
    addListeners('span[class^=subCategory-edit-]', 'click', sectionCatSubCatEdit, 'class', 'subcat', 'subCategory');
    addListeners('span[class^=section-delete-]', 'click', sectionCatSubCatDelete, 'class', 'sections', 'section');
    addListeners('span[class^=category-delete-]', 'click', sectionCatSubCatDelete, 'class', 'cat', 'category');
    addListeners('span[class^=subCategory-delete-]', 'click', sectionCatSubCatDelete, 'class', 'subcat', 'subCategory');
}

function addListeners(selector, type, func, attr, url = null, elem = null) {
    let selectorsAll = document.querySelectorAll(selector)
    selectorsAll.forEach(function (el) {
        el.addEventListener(type, function (e) {
            let getAttr = e.target.getAttribute(attr);
            if (elem != null && url != null)
                func(getAttr, url, elem); // edit: sections, cats, subcats
            else
                func(getAttr);
        })
    })
}

function sendRequest(method, url, body = null) {
    return fetch(url, {
        method: method,
        body: (body != null) ? JSON.stringify(body) : null,
        headers: {'Content-Type': 'application/json'}
    }).then(response => {
        if (response.ok)
            return response.json()
        return response.text().then(error => {
            // const e = new Error(error)
            // e.data = error
            // throw e
            throw(JSON.parse(error));
        })
    })
}

// function removeAllChilds(elem) {
//     if (elem.childElementCount > 0) {
//         while (elem.lastElementChild) {
//             elem.removeChild(elem.lastElementChild);
//         }
//     }
// }
//
//
// function showHideElement(id) {
//     let element = id;
//     if (typeof id === 'string')
//         element = getQSelector(id);
//     let display = element.style.display;
//     if (display !== "block")
//         element.style.display = "block";
//     else
//         element.style.display = "none";
// }

function getQSelector(el) {
    return document.querySelector(el);
}

function createInputField(tagName, type, className, value, minLength, maxLength) {
    let el = document.createElement(tagName);
    if (type != null)
        el.type = type;
    el.className = className;
    el.value = value
    el.minLength = minLength
    el.maxLength = maxLength
    return el;
}

function topicEditSendRequest(inputTitle, topicId, titleClone, newButton) {
    let body = {
        title: inputTitle.value
    }
    sendRequest('PUT', API_URL + '/topic/' + topicId, body)
        .then(data => {
            titleClone.innerText = data.title
            inputTitle.parentNode.replaceChild(titleClone, inputTitle)
            inputTitle.remove()
            newButton.remove()
        })
        .catch(err => {
            showErrResponse(err, inputTitle)
        })
}

function topicEdit(el) {
    let topicId = +/\d+/.exec(el);
    let title = getQSelector('#topic-title-' + topicId);
    let button = getQSelector('#' + el);
    let titleClone = title.cloneNode(false);
    let inputTitle = createInputField("input", "text",
        "form-control", title.textContent, 2, 128)

    title.parentNode.replaceChild(inputTitle, title)

    let newButton = button.cloneNode(true);
    newButton.textContent = 'Сохранить'

    button.parentNode.replaceChild(newButton, button);
    newButton.addEventListener('click', function (event) {
        topicEditSendRequest(inputTitle, topicId, titleClone, newButton);
    });
}

function showErrResponse(err, el) {
    let error = document.createElement('div');
    error.setAttribute('class', 'p-2 text-danger-emphasis bg-danger-subtle border border-danger-subtle rounded-3')
    error.innerText = err.message
    el.parentNode.insertBefore(error, el.nextSibling)
    setTimeout(() => {
        error.remove()
    }, 1500)
}

function editTopicMessageSendRequest(textarea, messageId, message, newButton) {
    let body = {
        topicId: getQSelector('#topicId').value,
        text: textarea.value
    }
    sendRequest('PUT', API_URL + '/topic-message/' + messageId, body)
        .then(data => {
            message.innerText = data.text;
            newButton.remove()
            textarea.remove();
        })
        .catch(err => {
            showErrResponse(err, message);
        })
}

function editTopicMessage(el) {
    let message = getQSelector('#' + el);
    let button = getQSelector('button[name="' + el + '"]')
    let messageId = +/\d+/.exec(el);
    let textarea = createInputField("textarea", null,
        "form-control", message.textContent, 2, 4000)
    textarea.rows = 7
    message.innerHTML = ''
    message.appendChild(textarea)
    button.innerHTML = "Сохранить"

    let newButton = button.cloneNode(true);
    button.parentNode.replaceChild(newButton, button);
    newButton.addEventListener('click', function (event) {
        editTopicMessageSendRequest(textarea, messageId, message, newButton);
    });
}

function sectionCatSubCatEdit(el, url, editableElement) {
    let id = +/\d+/.exec(el);
    let title = getQSelector('#' + editableElement + '-title-' + id);
    let description = getQSelector('#' + editableElement + '-description-' + id);
    let editButton = getQSelector('.' + el)
    editButton.style.display = 'none'
    let titleClone = title.cloneNode(false);
    let descriptionClone = description.cloneNode(false);

    let inputTitle = createInputField("input", "text",
        "form-control", title.textContent.trim(), 2, 128)
    title.parentNode.replaceChild(inputTitle, title)

    let textarea = createInputField("textarea", null,
        "form-control", description.textContent.trim(), 0, 512)
    textarea.rows = 5
    description.parentNode.replaceChild(textarea, description)

    let button = document.createElement('button');
    button.className = "btn btn-primary btn-sm btn-light"
    button.innerText = "Сохранить"
    button.addEventListener('click', () => {
        sectionCatSubCatEditSendRequest(inputTitle, textarea, id, descriptionClone, titleClone, button, editButton, url);
    })
    textarea.parentNode.insertBefore(button, textarea.nextSibling);
}

function sectionCatSubCatEditSendRequest(inputTitle, textarea, id, descriptionClone, titleClone, button, editButton, url) {
    let body = {
        title: inputTitle.value,
        description: textarea.value
    }
    sendRequest('PUT', API_URL + '/' + url + '/' + id, body)
        .then(data => {
            descriptionClone.innerText = data.description.trim()
            titleClone.innerText = data.title.trim()
            inputTitle.parentNode.replaceChild(titleClone, inputTitle)
            textarea.parentNode.replaceChild(descriptionClone, textarea)
            inputTitle.remove()
            button.remove()
            textarea.remove()
            editButton.style.display = ''
        })
        .catch(err => {
            showErrResponse(err, inputTitle)
        })
}

function deleteTopicMessage(el) {
    let id = +/\d+/.exec(el);
    let topicMessage = getQSelector('#' + el);
    if (confirm(messageIfDeleteAction)) {
        sendRequest('DELETE', API_URL + '/topic-message/' + id)
            .then(() => {
                topicMessage.parentNode.removeChild(topicMessage);
            })
            .catch(err => {
                showErrResponse(err, topicMessage)
            })
    }
}

function deleteTopic(el) {
    let id = +/\d+/.exec(el);
    let topic = getQSelector('#topic-' + id);
    if (confirm(messageIfDeleteAction)) {
        sendRequest('DELETE', API_URL + '/topic/' + id)
            .then(() => {
                topic.parentNode.removeChild(topic);
            })
            .catch(err => {
                showErrResponse(err, topic)
            })
    }
}

function sectionCatSubCatDelete(el, url, elem) {
    let id = +/\d+/.exec(el);
    let elemId = getQSelector('#' + elem + '-' + id);
    if (confirm(messageIfDeleteAction)) {
        sendRequest('DELETE', API_URL + '/' + url + '/' + id)
            .then(() => {
                elemId.parentNode.removeChild(elemId)
            })
            .catch(err => {
                showErrResponse(err, elemId)
            })
    }
}

function arrayToObject(arr) {
    let rv = {};
    for (let i = 0; i < arr.length; ++i)
        if (arr[i] !== undefined) rv[i] = arr[i];
    return rv;
}

function moveTopicsSendRequest(selectCategory, selectSubCategory) {
    let checkBoxesChecked = document.querySelectorAll('input[type="checkbox"]:checked');
    let checkedValues = Array.from(checkBoxesChecked).map(cb => cb.value);

    let cat = Number(selectCategory.value);
    let subCat = (Number(selectSubCategory.value) === 0) ? '' : '/subCat/' + Number(selectSubCategory.value)

    sendRequest('PATCH', API_URL + '/topic/cat/' + cat + subCat, checkedValues)
        .then(() => {
            for (let val of checkBoxesChecked.values()) {
                let topic = getQSelector('#topic-' + val.value);
                topic.parentNode.removeChild(topic);
            }
            moveDeleteTopicsRemoveElements();
        })
}

function createElementSelectListForMoveDeleteTopics(id, name) {
    let subCategoriesSelect = document.createElement("select");
    subCategoriesSelect.id = id
    subCategoriesSelect.name = name
    subCategoriesSelect.className = "form-select form-select-sm ms-2 d-inline"
    subCategoriesSelect.style.width = '300px'
    return subCategoriesSelect;
}

function fillCatSubCatForMoveTopics() {
    let category = {'ID': [], 'title': []}
    let subCategory = {'ID': [], 'categoryId': [], 'title': []}
    let c = 0;
    for (let i = 0; i < getCatAndSubCats.length; i++) {
        category.ID.push(getCatAndSubCats[i].id);
        category.title.push(getCatAndSubCats[i].title);
        for (let j = 0; j < getCatAndSubCats[i].subCategory.length; j++) {
            subCategory.ID.push(getCatAndSubCats[i].subCategory[j].id);
            subCategory.categoryId.push(getCatAndSubCats[i].subCategory[j].categoryId);
            subCategory.title.push(getCatAndSubCats[i].subCategory[j].title);
            c++;
        }
    }
    return {category, subCategory};
}

function set_select(name, arr_text, arr_val) {
    let select = document.getElementsByName(name)[0];
    select.options.length = 0;
    for (let k = 0; k < arr_val.length; k++) {
        select.options[k] = new Option(arr_text[k], arr_val[k]);
    }
}

let getCatAndSubCatsBoolean = false;
let getCatAndSubCats;

function moveDeleteTopicsRemoveElements() {
    getCatAndSubCatsBoolean = false
    let subCategory = getQSelector('#subCategory');
    let category = getQSelector('#category');
    let submitMove = getQSelector('#submitButtonMoveTopics');
    let submitDelete = getQSelector('#submitButtonDeleteTopics');
    if (category != null && subCategory != null) {
        subCategory.parentNode.removeChild(subCategory);
        category.parentNode.removeChild(category);
        submitMove.parentNode.removeChild(submitMove);
        submitDelete.parentNode.removeChild(submitDelete);
    }
}

function createElementSubmitMoveDeleteTopics(id, val) {
    let submit = document.createElement("input");
    submit.id = id
    submit.type = 'submit'
    submit.value = val
    submit.className = "btn btn-primary btn-sm btn-light"
    return submit;
}

function insertBefore(elem, node) {
    node.parentNode.insertBefore(elem, node)
}

function deleteTopicsSendRequest() {
    let checkBoxesChecked = document.querySelectorAll('input[type="checkbox"]:checked');
    let checkedValues = Array.from(checkBoxesChecked).map(cb => cb.value);

    sendRequest('DELETE', API_URL + '/topic', checkedValues)
        .then(() => {
            for (let val of checkBoxesChecked.values()) {
                let topic = getQSelector('#topic-' + val.value);
                topic.parentNode.removeChild(topic);
            }
            moveDeleteTopicsRemoveElements();
        })
}

async function moveDeleteTopics(el) {
    let selectedCheckBoxes = document.querySelectorAll('input[type="checkbox"]:checked');
    if (selectedCheckBoxes.length > 0) {
        if (getCatAndSubCatsBoolean === false) {
            getCatAndSubCats = await function () {
                return new Promise((resolve, reject) => {
                    resolve(sendRequest('GET', API_URL + '/cat'));
                    getCatAndSubCatsBoolean = true;
                });
            }();

            let topicsTable = getQSelector('#topics-table');
            let subCategoriesSelect = createElementSelectListForMoveDeleteTopics("subCategory", "subCategory");
            let categoriesSelect = createElementSelectListForMoveDeleteTopics("category", "category");
            let submitMove = createElementSubmitMoveDeleteTopics('submitButtonMoveTopics', 'Переместить');
            let submitDelete = createElementSubmitMoveDeleteTopics('submitButtonDeleteTopics', 'Удалить');

            if (getQSelector('#category') == null) {
                insertBefore(categoriesSelect, topicsTable)
                insertBefore(subCategoriesSelect, topicsTable)
                insertBefore(submitMove, topicsTable)
                insertBefore(submitDelete, topicsTable)
            }
            let {category, subCategory} = fillCatSubCatForMoveTopics();
            let selectCategory = document.getElementsByName('category')[0];
            let selectSubCategory = document.getElementsByName('subCategory')[0];

            function change_select() {
                let j = selectCategory.selectedIndex || 0, name = ['Без подкатегории'], id = [0];
                j = category['ID'][j]
                for (let i = 0; i < subCategory['categoryId'].length; i++) {
                    if (subCategory['categoryId'][i] === j) {
                        name.push(subCategory['title'][i]);
                        id.push(subCategory['ID'][i]);
                    }
                }
                set_select('subCategory', name, id)
            }

            set_select('category', category['title'], category['ID'])
            change_select()
            selectCategory.addEventListener('change', change_select);
            submitMove.addEventListener('click', () => {
                moveTopicsSendRequest(selectCategory, selectSubCategory);
            })
            submitDelete.addEventListener('click', () => {
                deleteTopicsSendRequest();
            })
        }
    } else {
        moveDeleteTopicsRemoveElements();
    }

}



















