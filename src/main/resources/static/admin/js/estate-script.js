

function showAlertBeforeDelete(callback) {
    swal({
        title: "Xác nhận xóa",
        text: "Bạn có chắc chắn xóa những dòng đã chọn",
        type: "warning",
        showCancelButton: true,
        confirmButtonText: "Xác nhận",
        cancelButtonText: "Hủy bỏ",
        confirmButtonClass: "btn btn-success",
        cancelButtonClass: "btn btn-danger"
    }).then(function (res) {
        if (res.value) {
            callback();
        } else if (res.dismiss == 'cancel') {
            console.log('cancel');
        }
    });
}

/* search */
$('#btnSearch').click(function (e){
    e.preventDefault();
    $('#listFormBuilding').submit();
})
$('#btnSearchCustomer').click(function (e){
    e.preventDefault();
    $('#listFormCustomer').submit();
})

/* Add building */
$('#btnAddBuilding').click(function (e) {
    e.preventDefault();
    let data = {};
    let buildingTypes =[];
    let formdata = $('#formSubmit').serializeArray();
    $.each(formdata, function (i,v){
        if (v.name == 'buildingTypes'){
            buildingTypes.push(v.value);
        }else {
            data[""+v.name+""] = v.value;
        }
    })
    data['buildingTypes'] = buildingTypes;
    addBuilding(data);
})
function  addBuilding(data){
    $.ajax({
        url : '/api/building',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            console.log(result)
            window.location.href = "/building-list"
        },
        error: function (error) {
            console.log(error)
            window.location.href = "/building-list"
        }
    });
}

/* update building */
$('#btnUpdateBuilding').click(function (e) {
    e.preventDefault();
    let data = {};
    let buildingTypes =[];
    let dataForm = $('#formSubmit').serializeArray();
    $.each(dataForm, function (i,v){
        if (v.name == 'buildingTypes'){
            buildingTypes.push(v.value);
        }else {
            data[""+v.name+""] = v.value;
        }
    })
    let idOfBuilding = $('#buildlingid').val();
    data['buildingTypes'] = buildingTypes;
    data['id'] = idOfBuilding;
    updateBuilding(data);
})

function  updateBuilding(data){
    $.ajax({
        url : '/api/building',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            console.log(result)
            window.location.href = "/building-list"
        },
        error: function (error) {
            console.log(error)
            window.location.href = "/building-list"
        }
    });
}

/* delete building */

function warningBeforeDelete() {
    showAlertBeforeDelete(function () {
        event.preventDefault();
        let dataArray = $('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        deleteBuilder(dataArray);
    });
}
function deleteBuilder(data) {
    $.ajax({
        url: '/api/building',
        type: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (res) {
            window.location.href = "/building-list";
        },
        error: function (res) {
            window.location.href = "/building-list";
        }
    });
}



/* customer  */

$('#btnAddCustomer').click(function (e) {
    e.preventDefault();
    let data ={};
    let dataForm = $('#formSubmitCustomer').serializeArray();
    $.each(dataForm , function (i,v){
        data[""+v.name+""] = v.value;
    })

    addCustomer(data);
})
function  addCustomer(data){
    $.ajax({
        url : '/api/customer',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            console.log(result)
            window.location.href = "/customer-list"
        },
        error: function (error) {
            console.log(error)
            window.location.href = "/customer-list"
        }
    });
}

$('#btnUpdateCustomer').click(function (e) {
    e.preventDefault();
    let data ={};
    let dataForm = $('#formSubmitCustomer').serializeArray();
    $.each(dataForm , function (i,v){
        data[""+v.name+""] = v.value;
    })
    let idOfCustomer = $('#customerId').val();
    data['id'] = idOfCustomer;
    updateCustomer(data);
})

function  updateCustomer(data){
    $.ajax({
        url : '/api/customer',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            console.log(result)
            window.location.href = "/customer-list"
        },
        error: function (error) {
            console.log(error)
            window.location.href = "/customer-list"
        }
    });
}
function warningBeforeDeleteCustomer() {
    showAlertBeforeDelete(function () {
        event.preventDefault();
        let dataArray = $('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        deteleCustomer(dataArray);
    });
}
function deteleCustomer(data) {
    $.ajax({
        url: '/api/customer',
        type: 'DELETE',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (res) {
            window.location.href = "/customer-list";
        },
        error: function (res) {
            window.location.href = "/customer-list";
        }
    });
}

/* transaction */
$('.btnAddTransaction').click(function (e) {
    let idNoteForm = 'id_'+this.id;
    let note = document.getElementById(idNoteForm).value;
    let code = this.id;
    let customerId = $('#customerId').val();
    let data ={};
    data['note'] = note;
    data['code'] = code;
    data['customerId'] = customerId;
    addTransaction(data);
})
function  addTransaction(data){
    $.ajax({
        url : '/api/customer/transaction',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(data),
        dataType: 'json',
        success: function (result) {
            console.log(result)
            window.location.href = "/customer-list"
        },
        error: function (error) {
            console.log(error)
            window.location.href = "/customer-list"
        }
    })
}


/* user */
$('#btnAddUsers').click(function (event){
    event.preventDefault();
    let formData = $("#formEdit").serializeArray();
    let dataArray = {};
    $.each(formData, function (i, v) {
        dataArray["" + v.name + ""] = v.value;
    });
    let userName = dataArray['userName'];
    let roleCode = dataArray['roleCode'];
    if (userName != '' && roleCode != '') {
        addUser(dataArray);
    } else {
        window.location.href = "/user-edit?message=username_role_require";
    }

})

$('#btnUpdateUsers').click(function (e){
    e.preventDefault();
    let formData = $("#formEdit").serializeArray();
    let dataArray = {};
    $.each(formData, function (i, v) {
        dataArray["" + v.name + ""] = v.value;
    });
    let userId = $('#userId').val();
    let roleCode = dataArray['roleCode'];
    if (roleCode != '') {
        updateUser(dataArray, $('#userId').val());
    } else {
        window.location.href = "<c:url value='/admin/user-edit-"+userId+"?message=role_require'/>";
    }
})

function addUser(data) {
    $.ajax({
        url: '/api/user',
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (res) {
            console.log(res);
            window.location.href = "/user-list";
        },
        error: function (res) {
            console.log(res);
            window.location.href = "/user-list";
        }
    });
}

function updateUser(data, id) {
    $.ajax({
        url: '/api/user/' + id,
        type: 'PUT',
        dataType: 'json',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (res) {
            console.log(res);
            window.location.href = "/user-list";
        },
        error: function (res) {
            console.log(res);
            window.location.href = "/user-list";
        }
    });
}

/* pagination */


let totalPages = $('#totalPage').val();
let currentPage = $('#page').val();


$(function () {
    window.pagObj = $('#pagination').twbsPagination({
        totalPages: totalPages,
        visiblePages: 5,
        startPage: currentPage,
        onPageClick: function (event, page) {
            if (currentPage != page) {
                 $('#limit').val(3);
                 $('#page').val(page);
                $('#listFormBuilding').submit();
            }
            console.log(page);
        }
    })
});

