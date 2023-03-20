const API_URL = '/api';

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
    addListenersForSelectors('button[name^=topic-message-text-]', 'click', editTopicMessage, 'name');
    addListenersForSelectors('span[class^=section-edit-]', 'click', sectionEdit, 'class');
    addListenersForSelectors('span[id^=topic-edit-]', 'click', topicEdit, 'id');
}

function addListenersForSelectors(selector, type, func, attr) {
    let selectorsAll = document.querySelectorAll(selector)
    selectorsAll.forEach(function (el) {
        el.addEventListener(type, function (e) {
            func(e.target.getAttribute(attr));
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

    let newButton = button.cloneNode(true); // for remove event listener
    button.parentNode.replaceChild(newButton, button);
    newButton.addEventListener('click', function (event) {
        editTopicMessageSendRequest(textarea, messageId, message, newButton);
    });
}


function sectionEdit(el) {
    let sectionId = +/\d+/.exec(el);
    let title = getQSelector('#section-title-' + sectionId);
    let description = getQSelector('#section-description-' + sectionId);
    let titleClone = title.cloneNode(false);
    let descriptionClone = description.cloneNode(false);

    let inputTitle = createInputField("input", "text",
        "form-control", title.textContent, 2, 128)
    title.parentNode.replaceChild(inputTitle, title)

    let textarea = createInputField("textarea", null,
        "form-control", description.textContent, 0, 512)
    textarea.rows = 5
    description.parentNode.replaceChild(textarea, description)

    let button = document.createElement('button');
    button.className = "btn btn-primary btn-sm btn-light"
    button.innerText = "Сохранить"
    button.addEventListener('click', () => {
        sectionEditSendRequest(inputTitle, textarea, sectionId, descriptionClone, titleClone, button);
    })
    textarea.parentNode.insertBefore(button, textarea.nextSibling);
}

function sectionEditSendRequest(inputTitle, textarea, sectionId, descriptionClone, titleClone, button) {
    let body = {
        title: inputTitle.value,
        description: textarea.value
    }
    sendRequest('PUT', API_URL + '/sections/' + sectionId, body)
        .then(data => {
            descriptionClone.innerText = data.description
            titleClone.innerText = data.title
            inputTitle.parentNode.replaceChild(titleClone, inputTitle)
            textarea.parentNode.replaceChild(descriptionClone, textarea)
            inputTitle.remove()
            button.remove()
            textarea.remove()
        })
        .catch(err => {
            showErrResponse(err, inputTitle)
        })
}