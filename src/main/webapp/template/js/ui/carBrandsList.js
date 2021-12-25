function refreshCarBrands() {
    let mContent = '';
    $('#brands-container').html(mContent);
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/car-brands.ajax',
        dataType: 'json'
    }).done(function (data) {
        if (data === undefined) {
            return false;
        }
        for (let carBrand of data) {
            let actions =
                '<a href="http://localhost:8080/cars/car-brands.do?id=' + carBrand.id + '" class="btn btn-default"><i class="fa fa-edit"></i></a>\n'
                + '<a href="#" class="btn btn-default" onclick="carBrandDelete(' + carBrand.id + ')"><i class="fa fa-times"></i></a>\n';
            mContent +=
                '<tr>\n'
                + '<td>' + carBrand.id + '</td>\n'
                + '<td>' + carBrand.name + '</td>\n'
                + '<td>' + actions + '</td>\n'
                + '</tr>\n';

        }
        $('#brands-container').html(mContent);
    }).fail(function(err) {
        console.log(err);
    });
}

function carBrandDelete(brandId) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/cars/car-brands.ajax?id=' + brandId,
        statusCode: {
            404: function () {
                $('#err-text').val('Элемент с индексом ' + brandId + ' не найден.');
                $('.error-panel').removeClass('hidden');
            },
            403: function () {
                $('#err-text').html('Доступ запрещен. Авторизуйтесь чтобы изменять данные.');
                $('.error-panel').removeClass('hidden');
            },
            200: function () {
                refreshCarBrands();
            }
        }
    });
}

$(document).ready(function () {
    refreshCarBrands();
});