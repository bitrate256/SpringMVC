/*
 * 각 항목에 대한 입력 유효성 판단
 */
var space = /\s/g;
var label = "";
var check = {
	common: { 
		empty: { code: 'invalid', desc: label + ' 입력하세요.' }
		, space: { code: 'invalid', desc: '공백 없이 입력하세요.' }
		, valid: { code: 'valid' }
	}
	/* cntSearch : 검색 키워드 */
	, cntSearch: {
		max: { code: 'invalid', desc: '최대 20자 이내로 입력하세요.' }
	}
	, cntSearch_status: function(cntSearch) {
		label = $('[id=cntSearch]').attr('title');
		if(cntSearch == '') { return this.common.empty; }
		else if(cntSearch.length > 20) { return this.cntSearch.max; }
		else { return this.common.valid; }
	}
	/* name : 이름, 성함 */
	, name: {
		min: { code: 'invalid', desc: '최소 2자 이상 입력하세요.' }
		, max: { code: 'invalid', desc: '최대 20자 이내로 입력하세요.' }
		, invalid: { code: 'invalid', desc: '성함은 한글과 영문만 입력하세요.' }
	}
	, name_status: function(name) { 
		var reg = /[^ㄱ-ㅎ가-힣a-zA-Z]/g;
		label = $('[id=name]').attr('title');
		if(name == '') { return this.common.empty; }
		else if(name.match(space)) { return this.common.space; }
		else if(reg.test(name)) { return this.name.invalid; }
		else if(name.length < 2) { return this.name.min; }
		else if(name.length > 20) { return this.name.max; }
		else { return this.common.valid; }
	}
	/* id */
	, id: {
		min: { code: 'invalid', desc: '최소 4자 이상 입력하세요.' }
		, max: { code: 'invalid', desc: '최대 20자 이내로 입력하세요.' }
		, invalid: { code:'invalid', desc: 'ID는 영문과 숫자만 입력하세요.' }
		, valid: { code:'valid', desc: 'ID 중복을 확인하세요.' }
		, unusable: { code: 'invalid', desc: '이미 사용 중인 ID 입니다.' }
		, usable: { code: 'valid', desc: '사용 가능한 ID 입니다.'}
	}
	, id_usable: function(data) {
		if(data) { return this.id.usable; } 
		else { return this.id.unusable; }
	}
	, id_status: function(id) {
		var reg = /^[a-z|A-Z|0-9|]+$/;
		label = $('[name=id]').attr('title');
		if(id == '') { return this.common.empty; }
		else if(id.match(space)) { return this.common.space; }
		else if(reg.test(id)) { return this.id.invalid; }
		else if(id.length < 4) { return this.id.min; }
		else if(id.length > 20) { return this.id.max; }
		else { return this.id.valid; }
	}
	/* pwd */
	, pwd: {
		min: { code: 'invalid', desc: '최소 8자 이상 입력하세요.' }
		, max: { code: 'invalid', desc: '최대 20자 이내로 입력하세요.' }
		, valid: { code:'valid', desc: '사용 가능한 비밀번호입니다.' }
		, invalid: { code:'invalid', desc: '비밀번호는 영문 대/소문자, 숫자만 입력하세요.' }
		, lack: { code:'invalid', desc: '비밀번호는 영문 대/소문자, 숫자를 모두 포함해야 합니다.' }
		, equal: { code: 'valid', desc: '비밀번호가 일치합니다.' }
		, notEqual: { code: 'invalid', desc: '비밀번호가 일치하지 않습니다.' } 		
	}
	, pwd_status: function(pwd) {
		var reg = /[^a-zA-Z0-9]/g;
		label = $('[name = pw]').attr('title');
		var upper = /[A-Z]/g, lower = /[a-z]/g, digit = /[0-9]/g;
		if(pwd == '') { return this.common.empty; }
		else if(pwd.match(space)) { return this.common.space; }
		else if(reg.test(pwd)) { return this.pwd.invalid; }
		else if(pwd.length < 8) { return this.pwd.min; }
		else if(pwd.length > 20) { return this.pwd.max; }
		else if ( !upper.test(pwd) || !lower.test(pwd) || !digit.test(pwd) ) { return this.pwd.lack; }
		else { return this.pwd.valid; }
	}
	, pwd_check_status: function(pwd_check) { 
		label = $('[name=pwd_check]').attr('title'); 
		if (pwd_check == '') { return this.common.empty; }
		else if( pwd_check == $('[name=pwd]').val() ) { return this.pwd.equal; }
		else { return this.pwd.notEqual; }
	}
	/* title : 제목(국문) */
	, title: {
		max: { code: 'invalid', desc: '최대 40자 이내로 입력하세요.' }
		, invalid: { code:'invalid', desc: '제목의 양식이 올바르지 않습니다.' }
	}
	, title_status: function(title) {
		var reg = /[^ㄱ-ㅎ가-힣A-Za-z0-9_\`\~\!\@\#\$\%\^\&\*\(\)\-\=\+\\\{\}\[\]\'\"\;\:\<\,\>\.\?\/\s]/gm;
		label = $('[id = title]').attr('title');
		if(title == '') { return this.common.empty; }
		else if(title.length > 40) { return this.title.max; }
		else if(reg.test(title)) { return this.title.invalid; }
		else { return this.common.valid; }
	}
	/* titleEng : 제목(영문) */
	, titleEng: {
		max: { code: 'invalid', desc: '최대 40자 이내로 입력하세요.' }
		, invalid: { code:'invalid', desc: '제목의 양식이 올바르지 않습니다.' }
	}
	, titleEng_status: function(titleEng) {
		// 한글 제외 영문, 숫자, 특수문자 사용 가능
		var reg = /[^A-Za-z0-9_\`\~\!\@\#\$\%\^\&\*\(\)\-\=\+\\\{\}\[\]\'\"\;\:\<\,\>\.\?\/\s]/gm;
		label = $('[id = titleEng]').attr('title');
		if(titleEng == '') { return this.common.empty; }
		else if(titleEng.length > 40) { return this.titleEng.max; }
		else if(reg.test(titleEng)) { return this.titleEng.invalid; }
		else { return this.common.valid; }
	}
	/* cnt 내용(국문) */
	, cnt_status: function(cnt) {
		label = $('[id = cnt]').attr('title');
		if(cnt == '') { return this.common.empty; }
		else { return this.common.valid; }
	}
	/* cntEng : 내용(영문) */
	, cntEng: {
		invalid: { code:'invalid', desc: '내용의 양식이 올바르지 않습니다.' }
	}
	, cntEng_status: function(cntEng) {
		var reg = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/g;
		label = $('[id = cntEng]').attr('title');
		if(cntEng == '') { return this.common.empty; }
		else if(reg.test(cntEng)) { return this.cntEng.invalid; }
		else { return this.common.valid; }
	}
	/* regNoFront : 주민번호 앞자리 */
	, regNoFront: {
		min: { code: 'invalid', desc: '6자를 입력하세요.' }
		, max: { code: 'invalid', desc: '6자 초과로 입력하셨습니다.' }
		, invalid: { code:'invalid', desc: '숫자만 입력하세요.' }
	}
	, regNoFront_status: function(regNoFront) {
		var reg = /[^0-9]/g;
		label = $('[id = regNoFront]').attr('title');
		if(regNoFront == '') { return this.common.empty; }
		else if(regNoFront.match(space)) { return this.common.space; }
		else if(reg.test(regNoFront)) { return this.regNoFront.invalid; }
		else if(regNoFront.length < 6) { return this.regNoFront.min; }
		else if(regNoFront.length > 6) { return this.regNoFront.max; }
		else { return this.common.valid; }
	}
	/* tel */
	, tel: {
		invalid: { code: 'invalid', desc: '전화번호의 형식이 올바르지 않습니다.' }
	}
	, tel_status: function(tel) {
		var reg = /^\d{2,3}-\d{3,4}-\d{4}$/;
		label = $('[id = tel]').attr('title');
		if(tel == '') { return this.common.empty; }
		else if(tel.match(space)) { return this.common.space; }
		else if( !reg.test(tel) ) { return this.tel.invalid; }
		else { return this.common.valid; }
	}
	/* phone */
	, phone: {
		invalid: { code:'invalid', desc: '연락처의 형식이 올바르지 않습니다.' }
	}
	, phone_status: function(phone) {
		var reg = /^\d{3}-\d{3,4}-\d{4}$/;
		label = $('[id = phone]').attr('title');
		if(phone == '') { return this.common.empty; }
		else if(phone.match(space)) { return this.common.space; }
		else if( !reg.test(phone) ) { return this.phone.invalid; }
		else { return this.common.valid; }
	}
	/* email */
	, email: {
		valid: { code: 'valid', desc: '유효한 이메일입니다.' }
		, invalid: { code: 'invalid', desc: '유효하지 않은 이메일입니다.' }
	}
	, email_status: function(email) {
		var reg = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
		label = $('[id = email]').attr('title');
		if( email == '' ) { return this.common.empty; }
		else if(email.match(space)) { return this.common.space; }
		else if( reg.test(email) ) { return this.email.valid; }
		else { return this.email.invalid; } 
	}
	/* date (datepicker 사용) */
	, date_status: function(date) {
		label = $('[id = date]').attr('title');
		if( date == '' ) { return this.common.empty; }
		else { return this.common.valid; }
	}
	/* select */
	, select: {
		empty: { code: 'invalid', desc: label + ' 선택해주세요.' }
	}
	, select_status: function(select) {
		label = $('[id = select]').attr('title');
		if( select == '' ) { return this.select.empty; }
		else { return this.common.valid; }
	}
	/* radio */
	, radio: {
		empty: { code: 'invalid', desc: label + ' 선택해주세요.' }
	}
	, radio_status: function(radio) {
		label = $('[name=radio]').attr('title');
		var radioObj = $('input[name=radio]:checked').length;
		if( radioObj == 0 ) { return this.radio.empty; }
		else { return this.common.valid; }
	}
	/**/
	, tag_status: function(tag) {		
		var data = tag.val();
		tag = tag.attr('id');
		if(tag == 'cntSearch') { data = this.cntSearch_status(data); }
		else if(tag == 'name') { data = this.name_status(data); }
		else if(tag == 'id') { data = this.id_status(data); }
		else if(tag == 'pwd') { data = this.pwd_status(data); }
		else if(tag == 'title') { data = this.title_status(data); }
		else if(tag == 'titleEng') { data = this.titleEng_status(data); }
		else if(tag == 'cnt') { data = this.cnt_status(data); }
		else if(tag == 'cntEng') { data = this.cntEng_status(data); }
		else if(tag == 'regNoFront') { data = this.regNoFront_status(data); }
		else if(tag == 'tel') { data = this.tel_status(data); }
		else if(tag == 'phone') { data = this.phone_status(data); }
		else if(tag == 'email') { data = this.email_status(data); }
		else if(tag == 'date') { data = this.date_status(data); }
		else if(tag == 'select') { data = this.select_status(data); }
		else if(tag == 'radio') { data = this.radio_status(data); }
		return data;
	}
}
function item_check(item) {
	var data = check.tag_status(item);
	if(data.code == 'invalid') {
		alert(data.desc);
		item.focus();
		return false;
	}
	else { return true; }
}
/*
function search() {
	if(!item_check($('[name=cntSearch]'))) return;
}
function file_upload() {
	$(document).on('change', ''), function() {
	  if(!item_check($('[name=file]'))) return;
	  if(!item_check($('[name=image]'))) return;
	});
}
function register() {
	if(!item_check($('[name=name]'))) return;
	if(!item_check($('[name=id]'))) return;
	if(!item_check($('[name=pwd]'))) return;
	if(!item_check($('[name=title]'))) return;
	if(!item_check($('[name=titleEng]'))) return;
	if(!item_check($('[name=cnt]'))) return;
	if(!item_check($('[name=cntEng]'))) return;
	if(!item_check($('[name=tel]'))) return;
	if(!item_check($('[name=phone]'))) return;
	if(!item_check($('[name=email]'))) return;
	if(!item_check($('[name=date]'))) return;
	if(!item_check($('[name=regNoFront]'))) return;
	if(!item_check($('select[name=select]'))) return;
	if(!item_check($('input[name=radio]'))) return;
}
*/

/***/
//숫자만 입력 onKeyup="this.value=this.value.replace(/[^0-9]/g,'');"
//한글과 영어만 입력 onKeyup="this.value=this.value.replace(/[^ㄱ-ㅎ|가-힣|a-z|A-Z]/g,'');"
