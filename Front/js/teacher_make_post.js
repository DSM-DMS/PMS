CKEDITOR.replace('teacher-textarea');
let defaultId = "teacher-subject-card";
let defaultModalId = "add-subjective-card";
let idCount = 0;
const YesOrNoHTML = '<input type="text" class="teacher-q-title" placeholder="질문 제목">'+
                    '<div class="teacher-q-check-answer">'+
                        '<input type="checkbox" id="yes" name="" value="">'+
                        '<label for="yes"><span></span>예</label><input type="checkbox" id="no" name="" value=""><label for="no"><span></span>아니요</label>'+
                    '</div>'+
                    '<div class="add-subjective-card-onClick-modal" id="modal1" style="visibility: hidden;"></div>'+
                    '<button type="button" class="button add-q-button" id='+defaultId+idCount+'><i class="fa fa-plus" aria-hidden="true"></i></button>';

const Sentence ='<input type="text" class="teacher-q-title" placeholder="질문 제목">'+
                '<textarea id="teacher-q-contents" rows="" cols="" placeholder="설명"></textarea>'+
                '<button type="button" class="button add-q-button"><i class="fa fa-plus" aria-hidden="true"></i></button>';

const modalElement = '<div class="add-subjective-card-onClick-modal" id='+"modal"+idCount+' style="visibility: hidden;">'+
                '<div> <button type="button" class="changeSentence">주관식</button> </div>'+
                '<div> <button type="button" class="changeYesOrNo">객관식</button> </div>'+
                '<div> </div>'+
                '<div> <button type="button">필수</button> </div>' +
            '</div>';

function createDynamicId(currentId) {
    idCount++; 
    return currentId + idCount;
}



$(document).ready(function () {
    $("#add-subjective-card").click(function () {
        let div = document.createElement('div');
        div.setAttribute('id', createDynamicId(defaultId));
        div.setAttribute('class', 'teacher-subjective card');
        div.innerHTML = YesOrNoHTML+modalElement;
        $("#teacher-making-contents").append(div);
    });
    $('.changeSentence').click(function(){
        let currentNo=$(this).attr('id');

        if(currentNo.replace(/[^0-9]/g,"") === undefined) currentNo=0;
        else currentNo=currentNo.replace(/[^0-9]/g,"");
        $("#"+defaultId+currentNo).empty();
        $("#"+defaultId+currentNo).append = Sentence;
    });

    $('.changeYesOrNo').click(function(){
        let currentNo=$(this).attr('id');

        if(currentNo.replace(/[^0-9]/g,"") === undefined) currentNo=0;
        else currentNo=currentNo.replace(/[^0-9]/g,"");
        $("#"+defaultId+currentNo).empty();
        $("#"+defaultId+currentNo).append = YesOrNoHTML;
    });


    $('.add-q-button').click(function () {
        let currentNo=$(this).attr('id');

        if(currentNo.replace(/[^0-9]/g,"") === undefined) currentNo=0;
        else currentNo=currentNo.replace(/[^0-9]/g,"");

        if($('#modal'+currentNo).css('visibility') === 'hidden') $('#modal'+currentNo).css('visibility', 'visible');
        else $('#modal'+currentNo).css('visibility', 'hidden');
       
    });
});
