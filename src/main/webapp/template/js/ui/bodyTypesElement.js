function bodyTypeGetById(bodyTypeId) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/body-types.ajax?id=' + bodyTypeId,
        dataType: 'json'
    }).done(function (data) {
        $('#body-type-name').val(data.name);
    }).fail(function(err) {
        $('#err-text').val('Провал получения элемента с индексом ' + bodyTypeId);
        $('#err-data').val(err);
        $('.error-panel').removeClass('hidden');
    });
}

function bodyTypeUpdate(bodyTypeId) {
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:8080/cars/body-types.ajax',
        processData: false,
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify({
            id: bodyTypeId,
            name: $('#body-type-name').val()
        }),
        statusCode: {
            406: function () {
                $('#err-text').html('Провал изменения элемента индексом ' + roleId);
                $('.error-panel').removeClass('hidden');
            },
            403: function () {
                $('#err-text').html('Доступ запрещен. Авторизуйтесь чтобы изменять данные.');
                $('.error-panel').removeClass('hidden');
            },
            200: function () {
                location = 'http://localhost:8080/cars/body-types.do';
            }
        }
    });
}

$('#body-type-submit').click(function () {
    let bodyTypeId = Number($('#body-type-id').val());
    if (bodyTypeId > 0) {
        bodyTypeUpdate(bodyTypeId);
        return false;
    }
});

$(document).ready(function () {
    let bodyTypeId = Number($('#body-type-id').val());
    if (bodyTypeId > 0) {
        bodyTypeGetById(bodyTypeId);
    }
});