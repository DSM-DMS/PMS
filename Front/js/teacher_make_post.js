CKEDITOR.replace('teacher-textarea');
var defaultId = "teacher-subject-card";
var modalDefaultId = "modal";
var idCount = 0;
const YesOrNoHTML = '<input type="text" class="teacher-q-title" placeholder="질문 제목"><div class="teacher-q-check-answer"><input type="checkbox" id="yes" name="" value=""><label for="yes"><span></span>예</label><input type="checkbox" id="no" name="" value=""><label for="no"><span></span>아니요</label></div><div class="add-subjective-card-onClick-modal" id="modal1" style="visibility: hidden;"></div><button type="button" class="button add-q-button"><i class="fa fa-plus" aria-hidden="true"></i></button>';

function fn_createDynamicId(defaultId) { 
    idCount++; 
    return defaultId + idCount;
}
$(document).ready(function () {
    $("#add-subjective-card").click(function () {
        var div = document.createElement('div');
        div.setAttribute('data-no', fn_createDynamicId(defaultId));
        div.setAttribute('class', 'teacher-subjective card');
        div.innerHTML = YesOrNoHTML;
        $("#teacher-making-contents").append(div);
    });
    $('.add-q-button').click(function (event) {
        console.log($('#modal'));
        $('#modal').attr("visibility", "visible");
    });
});