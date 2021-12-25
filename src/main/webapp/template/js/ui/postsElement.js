function postGetById(postId) {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/posts.ajax?id=' + postId,
        dataType: 'json'
    }).done(function (data) {
        if (data === undefined) {
            $('#err-text').val('Провал получения элемента с индексом ' + postId);
            $('.error-panel').removeClass('hidden');
            return;
        }
        $('#author-name').html(data.author.name);
        $('#author-phone').html(data.author.phone);
        $('#author-email').html(data.author.email);
        $('#post-model').val(data.model);
        $('#post-price').val(data.price);
        $(`#post-body-type option[value="${data.bodyType.id}"]`).prop('selected', true);
        $(`#post-brand option[value="${data.carBrand.id}"]`).prop('selected', true);
        $('#post-description').html(data.description);
        if (data.sold > 0) {
            $('#post-sold').attr('checked','1');
        } else {
            $('#post-sold').removeAttr('checked');
        }
    }).fail(function(err) {
        $('#err-text').val('Провал получения элемента с индексом ' + postId);
        $('#err-data').val(err);
        $('.error-panel').removeClass('hidden');
    });
}

function refreshBodyTypes() {
    let mContent = '';
    $('#post-body-type').html(mContent);
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/body-types.ajax',
        dataType: 'json'
    }).done(function (data) {
        if (data === undefined) {
            return false;
        }
        for (let bodyType of data) {
            mContent +=
                '<option value="' + bodyType.id + '">' + bodyType.name + '</option>\n';
        }
        $('#post-body-type').html(mContent);
    }).fail(function(err) {
        console.log(err);
    });
}

function refreshCarBrands() {
    let mContent = '';
    $('#post-brand').html(mContent);
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/car-brands.ajax',
        dataType: 'json'
    }).done(function (data) {
        if (data === undefined) {
            return false;
        }
        for (let carBrand of data) {
            mContent +=
                '<option value="' + carBrand.id + '">' + carBrand.name + '</option>\n';
        }
        $('#post-brand').html(mContent);
    }).fail(function(err) {
        console.log(err);
    });
}

function postDelete(postId) {
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:8080/cars/posts.ajax?id=' + postId,
        statusCode: {
            404: function () {
                $('#err-text').val('Элемент с индексом ' + postId + ' не найден.');
                $('.error-panel').removeClass('hidden');
            },
            403: function () {
                $('#err-text').html('Доступ запрещен. Авторизуйтесь чтобы изменять данные.');
                $('.error-panel').removeClass('hidden');
            }
        }
    });
}

$('#post-delete').click(function () {
    let postId = Number($('#post-id').val());
    if (postId > 0) {
        postDelete(postId);
    }
});

$(document).ready(function () {
    let postId = Number($('#post-id').val());
    refreshBodyTypes();
    refreshCarBrands();
    if (postId > 0) {
        postGetById(postId);
    } else {
        $('#post-delete').attr('disabled', true);
    }

});