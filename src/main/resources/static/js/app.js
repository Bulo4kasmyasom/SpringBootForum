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

function buttonListenerTopicMessage() {
    let btns = document.querySelectorAll('button[name^=topic-message-text-]')
    btns.forEach(function (btn) {
        btn.addEventListener('click', function (e) {
            divToTextarea(e.target.name);
        })
    })
}

window.onload = () => {
    buttonListenerTopicMessage();

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


function getQSelector(idName) {
    return document.querySelector(idName);
}


function divToTextarea(el) {
    let divMessage = getQSelector('#' + el);
    let button = document.querySelector('button[name="' + el + '"]')
    let messageId = +/\d+/.exec(el);
    let textarea = document.createElement("textarea");
    textarea.setAttribute('class', 'form-control');
    textarea.value = divMessage.textContent;
    textarea.setAttribute('rows', '7');
    divMessage.innerHTML = ''
    // divMessage.parentNode.replaceChild(textarea, divMessage);
    divMessage.appendChild(textarea)
    button.innerHTML = "Сохранить"
    let newButton = button.cloneNode(true); // for remove event listener
    button.parentNode.replaceChild(newButton, button);

    newButton.addEventListener('click', function (event) {
        let body = {
            topicId: getQSelector('#topicId').value,
            text: textarea.value
        }
        sendRequest('PUT', API_URL + '/topic-message/' + messageId, body)
            .then(data => {
                divMessage.innerText = data.text;
                newButton.remove()
                textarea.remove();
            })
            .catch(err => {
                let error = document.createElement('div');
                error.setAttribute('class', 'p-2 text-danger-emphasis bg-danger-subtle border border-danger-subtle rounded-3')
                error.innerText = err.message
                divMessage.parentNode.appendChild(error)
                setTimeout(() => {
                    error.remove()
                }, 1500)
            })
    });
}