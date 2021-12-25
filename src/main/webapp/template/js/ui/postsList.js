function refreshPosts(brandId, bodyTypeId, authorId) {
    let mContent = '';
    $('#posts-container').html(mContent);
    let params = '';
    if (brandId !== null) {
        params += params.length > 0 ? '&brandId=' + brandId : 'brandId=' + brandId;
    }
    if (bodyTypeId !== null) {
        params += params.length > 0 ? '&bodyTypeId=' + bodyTypeId : 'bodyTypeId=' + bodyTypeId;
    }
    if (authorId !== null) {
        params += params.length > 0 ? '&authorId=' + authorId : 'authorId=' + authorId;
    }
    let sUrl = 'http://localhost:8080/cars/posts.ajax';
    if (params.length > 0) {
        sUrl += '?' + params;
    }
    $.ajax({
        type: 'GET',
        url: sUrl,
        dataType: 'json'
    }).done(function (data) {
        if (data === undefined) {
            return false;
        }
        for (let post of data) {
            let actions =
                '<a class="btn btn-default" href="http://localhost:8080/cars/posts.do?id=' + post.id + '"><i class="fa fa-edit"> Открыть</i></a>\n';
            let line =
                '<tr>\n'
                + '<td>' + post.model + '</td>\n'
                + '<td>' + post.bodyType.name + '</td>\n'
                + '<td>' + post.price + '</td>\n'
                + '<td>' + actions + '</td>\n'
                + '</tr>';
            mContent += line;
        }
        $('#posts-container').html(mContent);
    }).fail(function(err) {
        console.log(err);
    });
}

function refreshBodyTypes() {
    let mContent = '';
    $('#body-types').html(mContent);
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
                '<li><a href="#" onclick="refreshPosts(null, ' + bodyType.id + ', null)">' + bodyType.name + '</a></li>\n';
        }
        $('#body-types').html(mContent);
    }).fail(function(err) {
        console.log(err);
    });
}

function refreshCarBrands() {
    let mContent = '';
    $('#car-brands').html(mContent);
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/car-brands.ajax',
        dataType: 'json'
    }).done(function (data) {
        if (data === undefined) {
            return false;
        }
        for (let carBrand of data) {
            let element = '<li><a href="#" onclick="refreshPosts(' + carBrand.id + ', null, null)">' + carBrand.name + '</a></li>';
            mContent += element;
        }
        $('#car-brands').html(mContent);
    }).fail(function(err) {
        console.log(err);
    });
}

$(document).ready(function () {
    refreshPosts(null, null, null);
    refreshBodyTypes();
    refreshCarBrands();
});