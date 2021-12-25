function refreshBodyTypes() {
    let mContent = '';
    $('#body-types-container').html(mContent);
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/body-types.ajax',
        dataType: 'json'
    }).done(function (data) {
        if (data === undefined) {
            return false;
        }
        for (let bodyType of data) {
            let actions =
                '<a href="http://localhost:8080/cars/body-types.do?id=' + bodyType.id + '" class="btn btn-default"><i class="fa fa-edit"></i></a>\n'
                + '<a href="#" class="btn btn-default" onclick="bodyTypeDelete(' + bodyType.id + ')"><i class="fa fa-times"></i></a>\n';
            mContent +=
                '<tr>\n'
                + '<td>' + bodyType.id + '</td>\n'
                + '<td>' + bodyType.name + '</td>\n'
                + '<td>' + actions + '</td>\n'
                + '</tr>\n'
        }
        $('#body-types-container').html(mContent);
    }).fail(function(err) {
        console.log(err);
    });
}

function bodyTypeDelete(bodyTypeId) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/cars/body-types.ajax?id=' + bodyTypeId,
        statusCode: {
            404: function () {
                $('#err-text').val('Элемент с индексом ' + bodyTypeId + ' не найден.');
                $('.error-panel').removeClass('hidden');
            },
            403: function () {
                $('#err-text').html('Доступ запрещен. Авторизуйтесь чтобы изменять данные.');
                $('.error-panel').removeClass('hidden');
            },
            200: function () {
                refreshBodyTypes();
            }
        }
    });
}

$(document).ready(function () {
    refreshBodyTypes();
});