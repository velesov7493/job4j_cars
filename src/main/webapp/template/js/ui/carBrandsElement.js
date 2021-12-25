function carBrandGetById(carBrandId) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/car-brands.ajax?id=' + carBrandId,
        dataType: 'json'
    }).done(function (data) {
        $('#car-brand-name').val(data.name);
    }).fail(function(err) {
        $('#err-text').val('Провал получения элемента с индексом ' + carBrandId);
        $('#err-data').val(err);
        $('.error-panel').removeClass('hidden');
    });
}

function carBrandUpdate(carBrandId) {
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:8080/cars/car-brands.ajax',
        processData: false,
        contentType: 'application/json; charset=UTF-8',
        data: JSON.stringify({
            id: carBrandId,
            name: $('#car-brand-name').val()
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
                location = 'http://localhost:8080/cars/car-brands.do';
            }
        }
    });
}

$('#car-brand-submit').click(function () {
    let brandId = Number($('#car-brand-id').val());
    if (brandId > 0) {
        carBrandUpdate(brandId);
        return false;
    }
});

$(document).ready(function () {
    let brandId = Number($('#car-brand-id').val());
    if (brandId > 0) {
        carBrandGetById(brandId);
    }
});