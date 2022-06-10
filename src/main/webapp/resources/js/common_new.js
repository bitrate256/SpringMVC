/*********************************************************
        값을 체크하는 함수
 *********************************************************/
/**
 *  입력값이 NULL인지 체크
 */
function isNull(input) {
    if (input.value == null || input.value == "") {
        return true;
    }
    return false; 
}

// 클릭시 레이어 보이기감추기
function div_layer(idMyDiv){
     var objDiv = document.getElementById(idMyDiv);
 
     if(objDiv.style.visibility=="visible"){ objDiv.style.visibility = "hidden"; }
     else{ objDiv.style.visibility = "visible"; }
}


/**
 * 입력값에 스페이스 이외의 의미있는 값이 있는지 체크
 * ex) if (isEmpty(form.keyword)) {
 *         alert("검색조건을 입력하세요.");
 *     }
 */
function isEmpty(input) {
    if (input.value == null || input.value.replace(/ /gi,"") == "") {
        return true;
    }
    return false;
}

/**
 * 입력값에 특정 문자(chars)가 있는지 체크
 * 특정 문자를 허용하지 않으려 할 때 사용
 * ex) if (hasChars(form.name,"!,*&^%$#@~;")) {
 *         alert("이름 필드에는 특수 문자를 사용할 수 없습니다.");
 *     }
 */
function hasChars(input,chars) {
    for (var inx = 0; inx < input.value.length; inx++) {
       if (chars.indexOf(input.value.charAt(inx)) != -1)
           return true;
    }
    return false;
}

/**
 * 입력값이 특정 문자(chars)만으로 되어있는지 체크
 * 특정 문자만 허용하려 할 때 사용
 * ex) if (!hasCharsOnly(form.blood,"ABO")) {
 *         alert("혈액형 필드에는 A,B,O 문자만 사용할 수 있습니다.");
 *     }
 */
function hasCharsOnly(input,chars) {

    for (var inx = 0; inx < input.value.length; inx++) {
       if (chars.indexOf(input.value.charAt(inx)) == -1){

           return false;
       }
    }
    return true;
}

/**
 *  입력값에 숫자만 있는지 체크
 *  (번호 입력란 체크.
 *   금액입력란은 isNumComma를 사용해야 합니다.)
 */
function isNumber(input) {
    var chars = "0123456789";
    return hasCharsOnly(input,chars);
}

/**
 *  입력값이 숫자,대시(-)로 되어있는지 체크
 */
function isNumDash(input){
    var chars = "-0123456789";
    if(!hasCharsOnly(input,chars))
    {
        alert(input.name+"는 숫자와 '-'만 입력 가능합니다");
        input.select();
        return false;
    }
    else
        return true;
}

//숫자만 입력
function numberInput(obj){

    var strValue = obj.value;
    var strReturnValuecomas = "";

    for (i=0 ;i < strValue.length ;i++)
    {

        if(i == 0 && strValue.charAt(i) == "0"){    //0으로 시작할수없음
            strReturnValuecomas = "";
        }else{
            if((strValue.charAt(i) == "1" )||(strValue.charAt(i) == "2" )||(strValue.charAt(i) == "3" )||(strValue.charAt(i) == "4" )||(strValue.charAt(i) == "5" )||(strValue.charAt(i) == "6" )||(strValue.charAt(i) == "7" )||(strValue.charAt(i) == "8" )||(strValue.charAt(i) == "9" )||(strValue.charAt(i) == "0" )){
                strReturnValuecomas += strValue.charAt(i);

            }
        }
    }

    obj.value= strReturnValuecomas;
}

/**
 *  입력값이 숫자,대시(-)로 되어있는지 체크
 *  (전화번호 입력란 체크)
 */
function isPhoneNum(input){
    var chars = "-0123456789";
    if(!hasCharsOnly(input,chars))
    {
        alert("전화번호는 숫자와 '-'만 입력 가능합니다");
        input.select();
        return false;
    }
    else
        return true;
}
/**
 *  입력값이 숫자,콤마(,)로 되어있는지 체크
 *  (금액 입력란 체크)
 */
function isNumComma(input){
    var chars = ",0123456789";
    if(!hasCharsOnly(input,chars))
    {
        alert(input.name+"에는 숫자와 ','만 입력 가능합니다");
        input.select();
        return false;
    }
    else
        return true;
}

/**
 *  입력값이 숫자,콤마(,)로 되어있는지 체크
 *  (금액 입력란 체크)
 */
function isNumberComma(input){
    var chars = ",0123456789";
    if(!hasCharsOnly(input,chars))
    {
        input.select();
        return false;
    }
    else
        return true;
}



/**
 *  영문만 입력 가능
 */
function isAlphabet(input){

    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
    return hasCharsOnly(input,chars);
}

/**
 *  orgChar 문자열에서 rmChar문자열을 없애고 리턴한다
 *  계좌번호나 금액에서 '-'나 ','를 제거할때 사용한다
 */
function removeChar(orgChar, rmChar){
    return replace(orgChar,rmChar,"");
}

/**
 * 입력값에서 콤마를 없애고 문자열 리턴. --> 되도록 removeChar 로 쓰세요. (-_-)
 */
function removeComma(input) {
    return input.value.replace(/,/gi,"");
}

/**
 *  입력값에서 '-'를 빼고 set --> 요것도.. 되도록 removeChar 로 쓰세요. (-_-)
 */
function setUnFormat(input){
    input.value = replace(input.value,"-","");
}

/**
 *  패스워드 입력란 체크
 *  check : size 4 , 숫자만입력
 */
function isPassword(input)
{
    var chars = "0123456789";
    if(isEmpty(input))
    {
        alert(input.name+'를 입력하십시오');
        input.select();

        return false;
    }

    else if(!hasCharsOnly(input,chars))
    {
        alert(input.name+'는 숫자만 입력 가능합니다');
        input.select();
        return false;
    }

    else if(input.value.length != 4)
    {
        alert(input.name+' 길이는 4자리입니다');
        input.select();
        return false;
    }
    else
        return true;
}

/**
 *  특수문자 있는지 확인
 *  있으면 false, 없으면 true리턴
 */
function hasPeculChar(input)
{
    var chars = trim(input.value);
    if(chars.length == 0)
        return true;
    else
    {
        for(i=0;i<chars.length;i++)
        {
            var a = chars.charCodeAt(i);
            if((a > 32 && a < 48) || (a > 57 && a < 65) || (a > 90 && a < 97))
                return false;
        }
        return true;
    }
}

/**
 *  한글만 가능
 */
function isHangul(input)
{
    var str = input.value;
    for(var idx=0;idx < str.length;idx++)
    {
        var c = escape(str.charAt(idx));
        if ( c.indexOf("%u") == -1 )
        {
            return false;
        }
    }
    return true;
}

function isHanOrPecChar(input)
{
    alert(hasPeculChar(input)+':'+isHangul(input));
    if((!hasPeculChar(input)) || (isHangul(input)))
        return false;
    else
        return true;

}

/**
 *  영문 & 숫자만 입력 가능
 */
function isAlphaNum(input){
    var chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";
    return hasCharsOnly(input,chars);
}

/**
 *  한글 입력 불가
 *  한글 있으면 false, 아니면 true리턴
 */

function preventHan(input){
    var chars = input.value;
    for(i=0;i<chars.length;i++) {
        var a = chars.charCodeAt(i);
        if (a > 128) {
            alert(input.name+'에 한글을 입력할 수 없습니다');
            input.select();
            return false;
        }
    }
    return true;
}

/****
 *  외환 에서 영문필드 체크
 *  case --> to upper case
 *  한글입력 불가
 */
function checkEngField(input)
{
    if(!preventHan(input))
    {
        return;
    }
    else
        input.value=input.value.toUpperCase();
}

function CheckKeys() // 문자입력 금지 함수 설정
{
    if (event.keyCode >= 48 && event.keyCode <= 57) event.returnValue = true;
    else                                            event.returnValue = false;
}



/****
 *  외환 에서 숫자필드 체크
 */
function checkNumField(input)
{
    if(!isNumber(input))
    {
        alert(input.name+'를 숫자로 입력하십시오');
        input.select();
        return false
    }
    return true;
}

/**
 *  주어진 길이에 맞게 c을 채운다(뒷쪽으로)
 *  fillChar(input, 5, '0') --> (input.value :22) 22000
 */
function fillChar(input, leng, c)
{
    var i;
    var rtn = "";
    var val = input.value;
    for ( i = 0; i < leng - val.length; i++ )
    {
        rtn = c + rtn;
    }
    rtn = val + rtn;
    input.value = rtn;
}


/**
 * 입력값의 바이트 길이를 리턴
 * ex) if (getByteLength(form.title) > 100) {
 *         alert("제목은 한글 50자(영문 100자) 이상 입력할 수 없습니다.");
 *     }
 */
function getByteLength(input){
    var byteLength = 0;
    for (var inx = 0; inx < input.value.length; inx++) {
        var oneChar = escape(input.value.charAt(inx));
        if ( oneChar.length == 1 ) {
            byteLength ++;
        } else if (oneChar.indexOf("%u") != -1) {
            byteLength += 2;
        } else if (oneChar.indexOf("%") != -1) {
            byteLength += oneChar.length/3;
        }
    }
    return byteLength;
}

/**
 * 문자열의 바이트 길이를 리턴
 * ex) if (getByteLength(form.title) > 100) {
 *         alert("제목은 한글 50자(영문 100자) 이상 입력할 수 없습니다.");
 *     }
 */
function getByte(s){
   var len = 0;
   if ( s == null ) return 0;
   for(var i=0;i<s.length;i++){
      var c = escape(s.charAt(i));
      if ( c.length == 1 ) len ++;
      else if ( c.indexOf("%u") != -1 ) len += 2;
      else if ( c.indexOf("%") != -1 ) len += c.length/3;
   }
   return len;
}

/**
 *  문자열에 있는 특정문자패턴을 다른 문자패턴으로 바꾸는 함수.
 */
function replace(targetStr, searchStr, replaceStr)
{
    var len, i, tmpstr;

    len = targetStr.length;
    tmpstr = "";

    for ( i = 0 ; i < len ; i++ ) {
        if ( targetStr.charAt(i) != searchStr ) {
            tmpstr = tmpstr + targetStr.charAt(i);
        }
        else {
            tmpstr = tmpstr + replaceStr;
        }
    }
    return tmpstr;
}

/**
 *  문자열에 있는 특정문자패턴을 다른 문자패턴으로 바꾸는 함수
 *  왼쪽부터 어떤 형을 삭제 하고 싶을때
 *  (ex)0000040540 ==> 40540
 *  다른문자열이 나올시 replace중단
 *  숫자앞에 0이 붙어서 나올시만 사용 바람.
 */
function replaceStr(targetStr, searchStr, replaceStr)
{
    var len, i, tmpstr;

    len = targetStr.length;
    tmpstr = "";

    for ( i = 0 ; i < len ; i++ ) {
        if ( targetStr.charAt(i) != searchStr ) {
            tmpstr = tmpstr + targetStr.charAt(i);
            searchStr ="A";
        }
        else {
            tmpstr = tmpstr + replaceStr;
        }
    }
    return tmpstr;
}

/**
 *  js에서 지원해주는 replace() 함수의 기능 보강
 */
function replaceAll( org, orgStr, chgStr ) {

    var retVal = "";

    if(org!=null && org!="") {

        var retVal = org;

        //해당 문자열이 몇번 반복되는지를 계산
        for( ; ; ) {

            if(retVal.indexOf(orgStr)<0) {
                break;
            } else {
                retVal = retVal.replace(orgStr, chgStr);
            }

        } //end of for

        return retVal;
    } //end of if

    return org;
}

//String 앞뒤의 공백을 제거한다.
function trim(str)
{
    var temp = null;
    temp = ltrim(str);
    str = rtrim(temp);
    return str;
}

//String 왼쪽의 공백을 제거한다.
function ltrim(str)
{
    var len = str.length;
    var i;
    for(i=0; i < len; i++) {
        if( str.charAt(i) != ' ' ) break
    }
    return str.substring(i, len);
}

//String 오른쪽의 공백을 제거한다.
function rtrim(str)
{
    var len = str.length;
    var i;
    for(i=len-1; i >= 0; i--) {
        if( str.charAt(i) != ' ' ) break
    }
    return str.substring(0, i+1);
}


/**
 *  2002.05.30.
 *  string, null -->  integer
 */
function toInt(str)
{
    var num = parseInt(str, 10);
    if(isNaN(num))
        return 0;
    else
        return num;
}


/**
 *  숫자를 금액형식으로 리턴 (000,000,000)
 */
function cashReturn(num)
{
    var numValue = ""+num;
    var cashReturn = "";
    for (var i = numValue.length-1; i >= 0; i--){
        cashReturn = numValue.charAt(i) + cashReturn;
        if (i != 0 && i%3 == numValue.length%3) cashReturn = "," + cashReturn;
    }
    return cashReturn;
}

/**
 *  금액 자동 설정 : input의 값을 amt만큼 더한다. (버튼클릭해서 자동으로 금액 세팅하는 화면에서 사용)
 *  amt 가 0인 경우, input의 값을 clear한다
 */
function setAmt(input, amt)
{
    var o_amt = toInt(input.value);
    if(amt == 0)
        input.value="";
    else
        input.value = (o_amt + amt);
}


function roundNum(arg1, arg2)
{
    var rtn = Math.floor(arg1 * Math.pow(10,arg2)) / Math.pow(10,arg2);
    return rtn;
}

/*****************************************************
        form 관련 함수
 *****************************************************/
/**
 * radio : 선택된 radio버튼이 있는지 체크
 */
function hasCheckedRadio(input) {
    if (input.length > 1) {
        for (var inx = 0; inx < input.length; inx++) {
            if (input[inx].checked) return true;
        }
    } else {
        if (input.checked) return true;
    }
    return false;
}

/**
 *  radio : radio에서 선택된 값을 가져온다.
 */
function getCheckedRadio(input)
{
    var val;
    var len = input.length;

    if(len > 1) {
        for(var i = 0 ; i < len ; i++) {
            if(input[i].checked == true)
                val = input[i].value;
        }
        return val;
    } else {
        if(input.checked == true)
            return input.value;
    }
}

/**
 *  checkbox : 선택된 체크박스가 있는지 체크
 */
function hasCheckedBox(input) {
    return hasCheckedRadio(input);
}

/**
 *  checkbox : 화면에 생성되어있는 모든 체크박스중 선택된 갯수를 구한다.
 */
function get_checked(){
    var checked_cnt = 0;
    for ( i = 0; i < document.forms.length; i++ ) {
        for ( j = 0; j < document.forms[i].elements.length; j++ ) {
            if ( document.forms[i].elements[j].type == 'checkbox' ) {
                if ( document.forms[i].elements[j].checked ) {
                    checked_cnt++;
                }
            }
        }
    }
    return checked_cnt;
}

/**
 * checkbox : 선택된 체크박스가 몇개인지 그 개수를 반환
 */
function hasMultiCheckedRadio(input) {
    var kkkk = 0;
    if (input.length > 1) {
        for (var inx = 0; inx < input.length; inx++) {
            if (input[inx].checked) {
            kkkk++;
            }
        }
    } else {
         if (input.checked) kkkk=1;
    }
    return kkkk;
}


/**
 * checkbox : disabled 체크박스가 몇개인지 그 개수를 반환
 */
function hasMultiDisabledRadio(input) {
    var kkkk = 0;
    if (input.length > 1) {
        for (var inx = 0; inx < input.length; inx++) {
            if (!input[inx].disabled) {
            kkkk++;
            }
        }
    } else {
         if (input.checked) kkkk=1;
    }
    return kkkk;
}

/**
 *  checkbox : 화면에 생성되어 있는 체크박스의 갯수를 리턴
 */
function getCheckBoxCnt()
{
    rtnCnt  =   0;
    for ( i=0;i < document.forms[0].elements.length;i++)
    {
        if  (document.forms[0].elements[i].type == "checkbox")
        {
            rtnCnt++;
        }
    }
    return  rtnCnt;
}

/**
 *  checkbox : 폼에 속에 있는 체크박스를 모두 선택/비선택하게 한다
 */
function setCheckBoxStatus(flag)
{
    for ( i=0;i < document.forms[0].elements.length;i++)
    {
        if  (document.forms[0].elements[i].type == "checkbox")
        {
            if  (flag   ==  "T")
            {
                document.forms[0].elements[i].checked=true;
            }
            else
            {
                document.forms[0].elements[i].checked=false;
            }
        }
    }
}

/**
 *  select : select에서 str값을 가진 option을 선택되도록 설정
 */
function setSelect(input,str) {
    for(i=0;i<input.options.length;i++){
        if(input.options[i].value == str)
            input.options[i].selected=true;
    }
}


/**
 *  select : select의 options들 다 삭제
 */
function clearOptions(obj)
{
//  var len = obj.length;
//  for(var i=0; i<len; i++)
//      obj.options[0]=null;

    obj.options.length = 0;
}

/**
 *  selectbox setting
 *  obj      : selectbox 이름
 *  arrTxt   : option의 이름 배열
 *  arrVal   : option의 값 배열
 *  selected : selectbox 세팅 후 초기선택값, 배열번호
 */
function makeSelectbox(obj, arrTxt, arrVal, selected) {

    if(obj && arrTxt.length>0) {

        //먼저 초기화
        clearOptions(obj);

        for(var i=0 ; i<arrTxt.length ; i++) {
            var txt = arrTxt[i];
            var val = arrVal[i];

            obc1 = new Option(txt);
            obj.options[i]          = obc1;
            obj.options[i].value    = val;
            obj.options[i].selected = selected;
        }
    }
}


/**
 *  select : select에서 선택된 값 리턴
 */
function getSelectedOption(obj)
{
    var idx = obj.selectedIndex;
    var v_sel = obj.options[idx].value;
    return v_sel;
}

/**
 *  콤보박스에서 선택된 옵션의 텍스트를 반환한다.
 */
function getSelectedOptionTxt(obj)
{
    var idx = obj.selectedIndex;
    if(obj.selectedIndex != -1) {
        var v_sel = obj.options[idx].text;
        return v_sel;
    } else return '';
}




/**
 *  새창 여는 함수(scrollbars=yes)
 */
function openWin(uri, winName, sizeW, sizeH)
{
    var nLeft = screen.width/2 - sizeW/2 ;
    var nTop  = screen.height/2 - sizeH/2 ;

    opt = ",toolbar=no,menubar=no,location=no,status=no,scrollbars=yes,resizable=yes";
    window.open(uri, winName, "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + opt );
}

/**
 *  새창 여는 함수(scrollbars=no)
 */
function openWinFix(uri, winName, sizeW, sizeH)
{
    var nLeft  = screen.width/2 - sizeW/2 ;
    var nTop  = screen.height/2 - sizeH/2 ;

    opt = ",toolbar=no,menubar=no,location=no,status=no,scrollbars=no,resizable=no";
    window.open(uri, winName, "left=" + nLeft + ",top=" +  nTop + ",width=" + sizeW + ",height=" + sizeH  + opt );
}


/**
 *  입력값에 maxlength="00" 으로 설정되어 있을 경우
 *  그 길이를 초과하였는지 리턴(한글일 경우 2byte 를 사용하므로 유용)
 *  해당 페이지에 있는 text, textarea, password의 값 모두 체크한다.
 *
 *  한글로 입력받는 field가 있는 경우, maxlength를 설정한 후에 submit하기 전에 isOverLen()를 사용해서 사이즈 초과되는 것을 잡아줄 수 있다.
 *
 *  ex) if (isOverLen()) return;
 */
function isOverLen(){
    for(frmIdx=0;frmIdx<window.document.forms.length;frmIdx++){
        objFrm=window.document.forms[frmIdx];
        for(elemIdx=0;elemIdx<objFrm.elements.length;elemIdx++){
            objElem=objFrm.elements[elemIdx];
            if( (objElem.type=="text") || (objElem.type=="textarea")  || (objElem.type=="password") ){
                if(objElem.maxLength != null){

                    if (objElem.maxLength < getByte(objElem.value)){
                        alert(objElem.name+"가 제한된 길이를 초과 하였습니다.\n다시 입력하여 주십시오.");
                        objElem.select();
                        return true;
                    }
                }
            }
        }
    }
    return false;
}

/**
 * SELECT에서 선택된 날짜를 'YYYYMMDD'형식의 문자열로 리턴
 */
function getDayString(obj_yy, obj_mm, obj_dd){

    var i_yy = obj_yy.selectedIndex;
    var i_mm = obj_mm.selectedIndex;
    var i_dd = obj_dd.selectedIndex;

    var v_yy = obj_yy.options[i_yy].value;
    var v_mm = obj_mm.options[i_mm].value;
    var v_dd = obj_dd.options[i_dd].value;

    return ""+v_yy+v_mm+v_dd;
}

/******************************************************************
 *      기타 특정형식의 값 체크
 ******************************************************************/
/**
 *  주민등록번호 체크.
 */
function isValidSsn(userSid1,userSid2){
   var ju = userSid1.value;
   var ju1 = userSid2.value;
   juid = new Array(0,0,0,0,0,0,0,0,0,0,0,0,0);

    if(!isNumber(userSid1) || !isNumber(userSid2))
        return false;

    if(getByteLength(userSid1)!=6 || getByteLength(userSid2)!=7)
        return false;

    for(var i = 0; i<6;i++)
        juid[i] = ju.substring(i,i+1);
    for(i=0;i<7;i++)
        juid[i+6] = ju1.substring(i,i+1);
    for(var sum = 0, i = 0;i<12;i++)
        sum += juid[i] * ((i >7) ? (i-6) : (i+2));

    var mod = 11 - sum%11;
    if(mod >= 10)
        mod -= 10;

    if(mod != juid[12])
          return false;
    else
          return true;
}

/**
 *  사업자등록번호 체크.
 */
function isValidOffNum(input){
    tmpStr          = input.value;
    tmpSum          = new Number(0);
    tmpMod          = new Number(0);
    resValue            = new Number(0);
    var intOffNo        = new Array(0,0,0,0,0,0,0,0,0,0);
    var strChkNum   = new Array(1,3,7,1,3,7,1,3,5);

    for(i = 0 ; i < 10 ; i ++){
        intOffNo[i] = new Number(tmpStr.substring(i, i+1));
    }

    for(i = 0 ; i < 9 ; i ++){
        tmpSum = tmpSum + (intOffNo[i]*strChkNum[i]);
    }

    tmpSum = tmpSum + ((intOffNo[8]*5)/10);

    tmpMod = parseInt(tmpSum%10, 10);

    if(tmpMod == 0){
        resValue = 0;
    }
    else{
        resValue = 10 - tmpMod;
    }

    if(resValue == intOffNo[9]){
        return true;

    }
    else{
        alert('유효한 사업자등록번호가 아닙니다');
        input.select();
        return false;
    }
}

/**
 * 자동 포커스 이동(현재객체, 이동객체, MaxLength)
 */
function autoFocus(input1, input2, maxLen) {
    if(input1.value.length == maxLen && event.keyCode != 9 && event.keyCode != 16 && event.keyCode != 37 && event.keyCode != 39 ) input2.focus() ;
}

/******************************************************************
 *      날짜 관련 function
 ******************************************************************/
/**
 * 두 날짜간 일자 차이를 리턴
 * date1:시작일자, date2:종료일자
 */
function getDayBetween(date1,date2) {
        var day_gab = Math.floor( (date1-date2) / (60*60*24*1000) )
        return (day_gab*-1) ;
}

/**
 *  연과 월을 파라메터로 넘겨주면 해당되는 월의 일 수를 리턴
 */
function getLastday(year,mon){
    if (mon == 4 || mon==6 || mon==9 || mon==11)
    {
        intLastDay=30;
    }
    else if (mon==2 && !(year % 4 == 0))
    {
        intLastDay=28;
    }
    else if (mon==2 && year % 4 == 0)
    {
        if (year % 100 == 0)
        {
            if (year % 400 == 0)
                intLastDay=29;
            else
                intLastDay=28;
        }
        else
        {
            intLastDay=29;
        }
    }
    else
    {
        intLastDay=31;
    }
    return intLastDay
}

/**
 *  선택한 년도, 월에 따라 일 select에 날짜를 display한다
 */
 function displayDay(obj_year, obj_month, obj_day) {
    var YEAR=obj_year.options[obj_year.selectedIndex].value;
    var MONTH=obj_month.options[obj_month.selectedIndex].value;
    var daysInMonth=new Date(new Date(YEAR,MONTH,1)-86400000).getDate();
    for(var i=0; i<obj_day.length; i++) obj_day.options[i]=null;
    for(var j=0; j<daysInMonth; j++) {
        if(j<9) var k="0"+(j+1); else var k=j+1;
        obj_day.options[j]=new Option(k, k);
    }
 }

/**
 *  fromDt, toDt간 날짜 간격 비교.. from < to이면 1을 , from > to이면 -1, 같으면 0리턴
 */
function getSequence(fromDt, toDt)
{
    var fromDate = new Date();
    var f_yy = fromDt.substr(0, 4);
    var f_mm = fromDt.substr(4, 2);
    var f_dd = fromDt.substr(6, 2);
    fromDate.setYear(f_yy);
    fromDate.setMonth(f_mm);
    fromDate.setDate(f_dd);

    var toDate = new Date();
    var t_yy = toDt.substr(0, 4);
    var t_mm = toDt.substr(4, 2);
    var t_dd = toDt.substr(6, 2);
    toDate.setYear(t_yy);
    toDate.setMonth(t_mm);
    toDate.setDate(t_dd);

    var interval = toDate-fromDate;

    if(interval > 0)
        return 1;
    else if(interval == 0)
        return 0;
    else
        return -1;
}

/**
 *  날짜를 y, m, d만큼 이동해서 리턴 (dt : YYYYMMDD(문자열), 리턴타입 : YYYYMMDD)
 *  y, m, d : +는 주어진 날짜를 앞으로 이동(더하기), -는 주어진 날짜를 뒤로 이동(빼기)
 */
function shiftDate(dt,y,m,d)
{
    var org_dt = new Date();
    var yy = dt.substr(0, 4);
    var mm = dt.substr(4, 2);
    var dd = dt.substr(6, 2);
    org_dt.setYear(yy);
    org_dt.setMonth(mm-1);
    org_dt.setDate(dd);
    var new_dt = org_dt;
    new_dt.setDate(org_dt.getDate() + d);
    new_dt.setMonth(new_dt.getMonth() + m);
    new_dt.setYear(new_dt.getYear() + y);

    var n_yy  = new_dt.getFullYear();
    var n_mm = new_dt.getMonth()+1;
    var n_dd   = new_dt.getDate();

    if (("" + n_mm).length == 1)    { n_mm = "0" + n_mm;    }
    if (("" + n_dd).length   == 1)  { n_dd = "0" + n_dd;  }

    return ""+n_yy+n_mm+n_dd;
}

/**
 *  시작일과 종료일이 있고, 종료일을 기준으로 시작일을 해당 interval만큼 계산해서 세팅한다.
 *  0 : 3일전
 *  1 : 1주일 전
 *  2 : 1개월 전
 *  3 : 3개월 전
 */
function changeDate(f_yy, f_mm, f_dd, t_yy, t_mm, t_dd, i)
{
    var dminus = 0;
    var mminus = 0;

    var from;
    var date=new Date();
    var yy;
    var oldfrdate1=new Date();
    var oldfr1yy;
    var minus;
    j=t_yy.selectedIndex;
    date.setYear(t_yy.options[j].value);
    j=t_mm.selectedIndex;
    date.setMonth(t_mm.options[j].value-1);
    j=t_dd.selectedIndex;
    date.setDate(t_dd.options[j].value);
    switch(i){
        case 0:
            dminus = 3
            from=date.getDate() - dminus;
            date.setDate(from);
            break;
        case 1:
            dminus = 6;
            from=date.getDate()- dminus;
            date.setDate(from);
            break;
        case 2:
            mminus = 1;
            from=date.getMonth()-mminus;
            date.setMonth(from);
            break;
        case 3:
            mminus = 3;
            from=date.getMonth()-mminus;
            date.setMonth(from);
            break;
        }
    yy=date.getYear();
    oldfrdate1.setYear(f_yy.options[0].text);
    olfr1yy=oldfrdate1.getYear();
    if(yy<olfr1yy){
        if(yy<2000){
                yy=yy+1900;
                f_yy.options[0].text=yy;
                for(j=1;j<t_yy.options.length;j++){
                f_yy.options[j].text=(yy+1);
                yy=yy+1;
                }
                f_yy.options[0].selected=true;
        }
    }   else if(yy<2000){

        yy=yy+1900;
        }
    for(j=0;j<t_yy.options.length;j++){
        if(f_yy.options[j].text==yy){
            f_yy.options[j].selected=true;
        }
    }

    for(j=0;j<t_mm.options.length;j++){
        if(f_mm.options[j].text==date.getMonth()+1){
        f_mm.options[j].selected=true;
        }
    }
    displayDay(f_yy, f_mm, f_dd);
    for(j=0;j<f_dd.options.length;j++){
        if(f_dd.options[j].text==date.getDate()){
            f_dd.options[j].selected=true;
        }
    }
}

/**
 *  조회 시작일과 종료일이 최근 n개월 안에 있는지 체크
 */
function isInRecentMonth(f_yy, f_mm, f_dd, t_yy, t_mm, t_dd, sys_date, term)
{
    var t_date = new Date();
    var f_date = new Date();
    var s_date = new Date();
    var p_date = new Date();

    f_date.setYear(f_yy.options[f_yy.selectedIndex].value);
    f_date.setMonth(f_mm.options[f_mm.selectedIndex].value);
    f_date.setDate(f_dd.options[f_dd.selectedIndex].value);

    t_date.setYear(t_yy.options[t_yy.selectedIndex].value);
    t_date.setMonth(t_mm.options[t_mm.selectedIndex].value);
    t_date.setDate(t_dd.options[t_dd.selectedIndex].value);

    s_date.setYear(sys_date.substring(0, 4));
    s_date.setMonth(sys_date.substring(4, 6));
    s_date.setDate(sys_date.substring(6, 8));

    p_date.setYear(sys_date.substring(0, 4));
    p_date.setMonth(sys_date.substring(4, 6));
    p_date.setDate(sys_date.substring(6, 8));

        var pp_date = shiftDate(sys_date, 0, -term, 0);
    var ss_date = f_yy.options[f_yy.selectedIndex].value + f_mm.options[f_mm.selectedIndex].value + f_dd.options[f_dd.selectedIndex].value;
    var ee_date = t_yy.options[t_yy.selectedIndex].value + t_mm.options[t_mm.selectedIndex].value + t_dd.options[t_dd.selectedIndex].value;

    if(term == 0)   return false;

    p_date.setMonth(p_date.getMonth()-term);

    var day   = 1000 * 3600 * 24; //24시간

    var s_day_int1 = parseInt((s_date - f_date) / day, 10);
    var s_day_int2 = parseInt((s_date - t_date) / day, 10);

    var p_day_int1 = parseInt((f_date - p_date) / day, 10) ;
    var p_day_int2 = parseInt((t_date - p_date) / day, 10) ;

    if((ss_date < pp_date) ||(ee_date < pp_date))
    {
        alert("기간을 최근 "+term+"개월 이내로 설정하십시오");
        return false;
    }
    else if((s_day_int1 < 0) || (s_day_int2 < 0))
    {
        alert("현재 날짜 이후로는 조회할 수 없습니다");
        return false;
    }
    else
    {
        return true;
    }
}



/**
 * 유효한(존재하는) 월(月)인지 체크
 */
function isValidMonth(mm)
{
    var m = parseInt(mm.value,10);
    return (m >= 1 && m <= 12);
}

/**
 * 유효한(존재하는) 일(日)인지 체크
 */
function isValidDay(yyyy, mm, dd) {

    var m = parseInt(mm,10) - 1;
    var d = parseInt(dd,10);

    var end = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
    if ((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0) {
        end[1] = 29;
    }

    return (d >= 1 && d <= end[m]);
}

/**
 * 유효한(존재하는) 시(時)인지 체크
 */
function isValidHour(hh) {
    var h = parseInt(hh,10);
    return (h >= 1 && h <= 24);
}

/**
 * 유효한(존재하는) 분(分)인지 체크
 */
function isValidMin(mi) {
    var m = parseInt(mi,10);
    return (m >= 1 && m <= 60);
}

/**
 * Time 형식인지 체크(느슨한 체크)
 */
function isValidTimeFormat(time) {
    return (!isNaN(time) && time.length == 12);
}

/**
 * 유효하는(존재하는) Time 인지 체크
 *     if (!isValidTime(time)) {
 *         alert("올바른 날짜가 아닙니다.");
 *     }
 */
function isValidTime(time) {
    var year  = time.substring(0,4);
    var month = time.substring(4,6);
    var day   = time.substring(6,8);
    var hour  = time.substring(8,10);
    var min   = time.substring(10,12);

    if (parseInt(year,10) >= 1900  && isValidMonth(month) &&
        isValidDay(year,month,day) && isValidHour(hour)   &&
        isValidMin(min)) {
        return true;
    }
    return false;
}

/**
 * 생년월일 만 나이 리턴(생년월일)
 */
function getAge(byear,mm,bday)
{
    byear = parseInt(byear);
    mm    = parseInt(mm);
    bday  = parseInt(bday);

    thedate = new Date();
    mm2   = thedate.getMonth() + 1;
    dd2   = thedate.getDate();
    yy2   = thedate.getYear();

    if (yy2 < 100) yy2 += 1900
    yourage = yy2 - byear;

    if (mm2 < mm) yourage--;
    if ((mm2 == mm) && (dd2 < bday)) yourage--;

    return yourage;
}

/**
 * 생년월일 만 나이 리턴(주민번호)
 */
function getManAge(jumin)
{
    var birday  = jumin;
    var byear   = birday.substring(0,2);
    var bmon    = birday.substring(2,4);
    var bday    = birday.substring(4,6);
    var gubun   = birday.substring(6,7);

    var today = new Date();
    var tyear = today.getYear();
    var tmon  = today.getMonth()+1;
    var tday  = today.getDate();
    var boAge = 0;

    if(birday.length == 0)
    {
        boAge = "";
    }
    else
    {
        if     (gubun == '1' || gubun == '2'){ byear = '19' + byear; }
        else if(gubun == '3' || gubun == '4'){ byear = '20' + byear; }

        if(bmon.length == 2 && bmon.substr(0,1) == "0") { bmon = bmon.substr(1,1); }
        if(bday.length == 2 && bday.substr(0,1) == "0") { bday = bday.substr(1,1); }

        if (tyear < 100) tyear += 1900
        boAge = parseInt(tyear) - parseInt(byear);

        if (eval(tmon) < eval(bmon)) boAge--;
        if ((eval(tmon) == eval(bmon)) && (eval(tday) < eval(bday))) boAge--;

    }
    return boAge;
}
/****************************************************
    MultiSelect 관련
*****************************************************/
function getSelectNum(objName)
{
    //var   obj =   document.all(objName);
    var obj =   objName;

    intLoop =   0;
    for ( i =0; i < obj.length; i ++)
    {
        if ( obj.options[i].selected )  intLoop++;
    }
    return  intLoop;
}

function InsertList(objName, strText, strValue)
{
    var i   =   0;
    //var   obj     =   document.all(objName);
    var obj     =   objName;
    obj.length++;

    if  (obj.selectedIndex  <   0)  obj.selectedIndex   =   0;

    for (i=obj.length-1;i > obj.selectedIndex;i--)
    {
        obj.options[i].text =   obj.options[i-1].text;
        obj.options[i].value    =   obj.options[i-1].value;
    }

    obj.options[obj.selectedIndex].text     =   strText;
    obj.options[obj.selectedIndex].value    =   strValue;

    obj.selectedIndex   =   obj.selectedIndex;

}
/**
 *  multi select 이동시 사용하는 method
 *  parameter : object의 name이 아니고 객체.
 */
function MoveArrow(objNameFrom, objNameTo)
{

    var i   =   0;
    var j   =   0;
    var k   =   0;

    //var   objFrom     =   document.all(objNameFrom);
    var objFrom     =   objNameFrom;

    var selectedText    =   new Array();
    var selectedValue   =   new Array();
    var unselectedText  =   new Array();
    var unselectedValue =   new Array();

    for ( i =0; i < objFrom.length; i ++)
    {
        if ( objFrom.options[i].selected )
        {
            selectedText[k]     =   objFrom.options[i].text;
            selectedValue[k]    =   objFrom.options[i].value;
            k++;
        }
        else
        {
            unselectedText[j]   =   objFrom.options[i].text;
            unselectedValue[j]  =   objFrom.options[i].value;
            j++;
        }
    }

    for (i=getSelectNum(objNameFrom)-1; i >= 0;i--)
    {
        InsertList(objNameTo, selectedText[i], selectedValue[i]);
    }

    objFrom.length  =   objFrom.length  -   getSelectNum(objNameFrom);

    for ( i=0; i<objFrom.length;i++)
    {
        objFrom.options[i].text     =   unselectedText[i];
        objFrom.options[i].value    =   unselectedValue[i];
    }
    if  (objFrom.selectedIndex  <   0)
        objFrom.selectedIndex   =   objFrom.length  -   1;
    else
        objFrom.selectedIndex   =   objFrom.selectedIndex;
}

function MoveUp(objName)
{
    //obj   =   document.all(objName);
    var obj =   objName;

    if  (getSelectNum(objName)  !=  1)
    {
        alert("이동시에는 한 행만 선택해야합니다.");
        return;
    }

    if  (obj.selectedIndex  ==  0)  return;

    var tmpText     =   obj.options[obj.selectedIndex].text;
    var tmpValue    =   obj.options[obj.selectedIndex].value;

    obj.options[obj.selectedIndex].text     =   obj.options[obj.selectedIndex-1].text;
    obj.options[obj.selectedIndex].value    =   obj.options[obj.selectedIndex-1].value;

    obj.options[obj.selectedIndex-1].text   =   tmpText;
    obj.options[obj.selectedIndex-1].value  =   tmpValue;

    obj.selectedIndex--;
}

function MoveDn(objName)
{
    //obj   = document.all(objName);
    var obj =   objName;

    if  (getSelectNum(objName)  !=  1)
    {
        alert("이동시에는 한 행만 선택해야합니다.");
        return;
    }

    if  (obj.selectedIndex  ==  obj.length-1)   return;

    var tmpText     =   obj.options[obj.selectedIndex].text;
    var tmpValue    =   obj.options[obj.selectedIndex].value;

    obj.options[obj.selectedIndex].text     =   obj.options[obj.selectedIndex+1].text;
    obj.options[obj.selectedIndex].value    =   obj.options[obj.selectedIndex+1].value;

    obj.options[obj.selectedIndex+1].text   =   tmpText;
    obj.options[obj.selectedIndex+1].value  =   tmpValue;

    obj.selectedIndex++;
}

/**
 *  조회기간 유효성 체크
 */
function isValidTerm(obj_yy1, obj_mm1, obj_dd1, obj_yy2, obj_mm2, obj_dd2)
{
    var date1 = obj_yy1.value + obj_mm1.value + obj_dd1.value;
    var date2 = obj_yy2.value + obj_mm2.value + obj_dd2.value;
    if (date1 > date2) {
        alert("조회기간 지정이 잘못되었습니다.");
        obj_yy1.focus();
        return false;
    }
    return true;
}


/******************************************
    Mini Calendar (2002. 06. 15)
*******************************************/

var target, target2, target3, target4, target5, target6;
var s1,s2,s3;

function setComboBox(targt, optName, optValue)
{
    last = targt.length;

    for(i=0; i<last; i++){
        if(targt.options[i].value == optValue){
            targt.selectedIndex = i
            targt.options[i].selected
            return
        }
    }
}

/**
 *  달력 display
 */
function MiniCal(jucke, juche2, juche3)
{

    target=jucke;
    target2=juche2;
    target3=juche3;

    x = (document.layers) ? loc.pageX : event.clientX;
    y = (document.layers) ? loc.pageY : event.clientY;
    if(document.all.minical.length > 1){
        minical[0].style.pixelTop   = y+10;
        //minical[0].style.pixelLeft    = x-150;
        minical[0].style.pixelLeft  = x-10;
        minical[0].style.display = (minical[0].style.display == "block") ? "none" : "block";
    }else{
        minical.style.pixelTop  = y+10;
        //minical.style.pixelLeft   = x-150;
        minical.style.pixelLeft = x-10;
        minical.style.display = (minical.style.display == "block") ? "none" : "block";
    }
    Show_cal(target.value,target2.value,target3.value);
}



var stime
function doOver() {
    var el = window.event.srcElement;
    cal_Day = el.title;

    if (cal_Day.length > 7) {
        el.style.borderTopColor = el.style.borderLeftColor = "buttonhighlight";
        el.style.borderRightColor = el.style.borderBottomColor = "buttonshadow";
    }
    window.clearTimeout(stime);
}

function doClick() {
    cal_Day = window.event.srcElement.title;
    window.event.srcElement.style.borderColor = "#990000";
    if (cal_Day.length > 7) {
        getFixed(cal_Day);
        setComboBox(target,s1,s1);
        setComboBox(target2,s2,s2);
        setComboBox(target3,s3,s3);

    }
}


function doOut() {
    var el = window.event.fromElement;
    cal_Day = el.title;

    if (cal_Day.length > 7) {
        el.style.borderColor = "white";
    }
    if(document.all.minical.length > 1){
        stime=window.setTimeout("minical[0].style.display='none';", 200);
    }else{
        stime=window.setTimeout("minical.style.display='none';", 200);
    }
}

function getFixed(sDate){
    var s;
    var arr;

    s = new String(sDate);
    arr = s.split("-");
    if(arr.length == 3){
        s = arr[0] + "-";
        if(arr[1].length == 1) arr[1] = "0" + arr[1];
        s1 = arr[0];
        s = s + arr[1] + "-";
        s2 = arr[1];
        if(arr[2].length == 1) arr[2] = "0" + arr[2];
        s3 = arr[2];
        s = s + arr[2];
    }else{
        s = sDate;
    }
    return s;
}

function Show_cal(sYear,sMonth,sDay)
{
    if(sYear<1997) return;
    if(document.all.minical.length > 1){
        document.all.minical[0].innerHTML="";
    }else{
        document.all.minical.innerHTML="";
    }
    Cal_HTML = "";
    //var datToday=new Date().toLocaleString();
    var datToday=new Date();
    intThisYear = sYear;
    intThisMonth = sMonth;
    intThisDay = sDay;

    if (intThisDay==0) intThisDay = datToday.getDay();
    if (intThisMonth==0) intThisMonth = datToday.getMonth();
    if (intThisYear==0) intThisYear = datToday.getYear();

    if (intThisMonth == 1)
    {
        intPrevYear=intThisYear-1;
        intPrevMonth=12;
        intNextYear=intThisYear;
        intNextMonth=2;
    }
    else if (intThisMonth==12)
    {
        intPrevYear=intThisYear;
        intPrevMonth=11;
        intNextYear=(parseInt(intThisYear) + 1);
        intNextMonth=1;
    }
    else
    {
        intPrevYear=intThisYear;
        intPrevMonth=intThisMonth -1;
        intNextYear=intThisYear;
        intNextMonth=Math.ceil(intThisMonth) + 1;
    }

    NowThisYear = sYear;
    NowThisMonth = sMonth;
    NowThisDay = sDay;

    if (NowThisDay==0) NowThisDay = datToday.getDay();
    if (NowThisMonth==0) NowThisMonth = datToday.getMonth();
    if (NowThisYear==0) NowThisYear = datToday.getYear();


    var first_date=new Date(intThisYear,intThisMonth-1,1)
        intFirstWeekday=first_date.getDay();
        intFirstWeekday++



    intSecondWeekDay=intFirstWeekday
    intThirdWeekDay=intFirstWeekday

    datThisDay= intThisYear.toString() +  "-" + intThisMonth.toString() + "-" + intThisDay.toString();
    intThisWeekday=first_date.getDay();
    intThisWeekday++

    if (intThisWeekday == 1) varThisWeekday = "일";
    if (intThisWeekday == 2) varThisWeekday = "월";
    if (intThisWeekday == 3) varThisWeekday = "화";
    if (intThisWeekday == 4) varThisWeekday = "수";
    if (intThisWeekday == 5) varThisWeekday = "목";
    if (intThisWeekday == 6) varThisWeekday = "금";
    if (intThisWeekday == 7) varThisWeekday = "토";


    intPrintDay=1;
    secondPrintDay=1;
    thirdPrintDay=1;

    Stop_Flag=0;



    if (intThisMonth == 4 || intThisMonth==6 || intThisMonth==9 || intThisMonth==11)
    {
        intLastDay=30;
    }
    else if (intThisMonth==2 && !(intThisYear % 4 == 0))
    {
        intLastDay=28;
    }
    else if (intThisMonth==2 && intThisYear % 4 == 0)
    {
        if (intThisYear % 100 == 0)
        {
            if (intThisYear % 400 == 0)
                intLastDay=29;
            else
                intLastDay=28;
        }
        else
        {
            intLastDay=29;
        }
    }
    else
    {
        intLastDay=31;
    }

    if (intPrevMonth==4 || intPrevMonth==6 || intPrevMonth==9 || intPrevMonth==11)
    {
        intPrevLastDay=30;
    }
    else if (intPrevMonth==2 &&  !(intPrevYear % 4 == 0))
    {
        intPrevLastDay=28;
    }
    else if (intPrevMonth==2 && intPrevYear % 4 == 0)
    {
        if (intPrevYear % 100 == 0)
        {
            if (intPrevYear % 400 == 0)
            {
                intPrevLastDay=29;
            }
            else
            {
                intPrevLastDay=28;
            }
        }
        else
        {
            intPrevLastDay=29;
        }
    }
    else
    {
        intPrevLastDay=31;
    }


    Stop_Flag=0;
    Cal_HTML=Cal_HTML + "<table border='0' bgcolor='#DCDCDC' cellpadding=1 cellspacing=1  onmouseover='doOver()' onmouseout='doOut()' onclick='doClick()' style='font-size : 12;font-family:굴림;'>";
    Cal_HTML=Cal_HTML + "<tr align=center>";
    Cal_HTML=Cal_HTML + "<td align=left  title='이전달' style='cursor:hand;' OnClick='JAVAScript:Show_cal(" + intPrevYear.toString()  + "," + intPrevMonth.toString() + ",1)'><font color=006699 size=2>◀</font></td>";
    Cal_HTML=Cal_HTML + "<td colspan=5><font color=#990000><b>";
    Cal_HTML=Cal_HTML + intThisYear.toString() + "년 " + intThisMonth.toString() + "월";
    Cal_HTML=Cal_HTML + "</font></b></td>";
    Cal_HTML=Cal_HTML + "<td align=right title='다음달' style='cursor:hand;' OnClick='JAVAScript:Show_cal(" + intNextYear.toString() + "," + intNextMonth.toString() + ",1)'><font color=006699 size=2>▶</font></a></td>";
    Cal_HTML=Cal_HTML + "</tr>";
    Cal_HTML=Cal_HTML + "<tr align=center bgcolor='8DCFF4' style='color:000000;'>";
    Cal_HTML=Cal_HTML + "<td>일</td><td>월</td><td>화</td><td>수</td><td>목</td><td>금</td><td>토</td>";
    Cal_HTML=Cal_HTML + "</tr>";



    for (intLoopWeek=1;intLoopWeek<=6;intLoopWeek++)
    {

        Cal_HTML=Cal_HTML + "<tr align=right valign=top bgcolor='#F6F9F3'>";
        for (intLoopDay=1;intLoopDay<=7;intLoopDay++)
        {
            if (intThirdWeekDay > 1)
            {
                Cal_HTML=Cal_HTML + "<td>&nbsp;</td>";
                intThirdWeekDay=intThirdWeekDay-1;
            }
            else
            {
                if (thirdPrintDay > intLastDay)
                {
                    Cal_HTML=Cal_HTML + "<td>&nbsp;</td>";
                }
                else
                {
                    Cal_HTML=Cal_HTML + "<td title='" + intThisYear.toString() + "-" + intThisMonth.toString() + "-" + thirdPrintDay.toString() + "' style='cursor: hand;border: 1px solid white;width:18; height:18;";
                    if (intThisYear-NowThisYear==0 && intThisMonth-NowThisMonth==0 && thirdPrintDay-intThisDay==0)
                    {
                        Cal_HTML=Cal_HTML + "background-color:#FACD8A;";
                    }

                    if  (intLoopDay==1)
                    {
                        Cal_HTML=Cal_HTML + "color:#990000;";
                    }
                    else
                    {
                        Cal_HTML=Cal_HTML + "color:black;";
                    }

                    Cal_HTML=Cal_HTML+ "'>" + thirdPrintDay.toString();
                }
                thirdPrintDay++;

                if (thirdPrintDay > intLastDay) Stop_Flag=1;

            }
            Cal_HTML=Cal_HTML + "</td>";
        }
        Cal_HTML=Cal_HTML + "</tr>";
        if (Stop_Flag==1) break;
    }


    Cal_HTML=Cal_HTML+ "</table>";
    if(document.all.minical.length > 1){
        document.all.minical[0].innerHTML=Cal_HTML;
    }else{
        document.all.minical.innerHTML=Cal_HTML;
    }

}

//////////////////////////////////////////////////////////////
function getBS(i){
    if(i%4 ==0){
        if(i%100 ==0){
            if(i%400==0) return true;
            return false;
        }else{
            if(i % 400 !=0) return true;
        }
    }
    return false;

}


function BS_Weekday(thisYear,thisMonth)
{
    totalday = 0;

    for (i=1997;i<thisYear;i++)
    {
        if(getBS(i))
            totalday += 366;
        else
            totalday += 365;
    }

    for (i=1;i<=thisMonth-1;i++)
    {
        if (i==1 || i==3 || i==5 || i==7 || i==8 || i==10 || i==12)
            totalday += 31;
        if (i==4 || i==6 || i==9 || i==11)
            totalday += 30;
        if (i==2)
        {
            if(getBS(thisYear))
                totalday += 29;
            else
                totalday += 28;
        }
    }

    totalday ++;
    return (((totalday+2) % 7) + 1);

}

function MiniCal_plan(jucke, juche2, juche3)
{

    target=jucke;
    target2=juche2;
    target3=juche3;

    x = (document.layers) ? loc.pageX : event.clientX;
    y = (document.layers) ? loc.pageY : event.clientY;
    if(document.all.minical.length > 1){
        minical[0].style.pixelTop   = y+10;
        //minical[0].style.pixelLeft    = x-150;
        minical[0].style.pixelLeft  = x-10;
        minical[0].style.display = (minical[0].style.display == "block") ? "none" : "block";
    }else{
        minical.style.pixelTop  = y+10;
        //minical.style.pixelLeft   = x-150;
        minical.style.pixelLeft = x-10;
        minical.style.display = (minical.style.display == "block") ? "none" : "block";
    }
    Show_plancal(target.value,target2.value,target3.value);

}


function Show_plancal(sYear,sMonth,sDay)
{
    //JINTEST
    if(sYear<1997){
        return;
    }

    if(document.all.minical.length > 1){
        document.all.minical[0].innerHTML="";
    }else{
        document.all.minical.innerHTML="";
    }
    Cal_HTML = "";

    //var datToday=new Date().toLocaleString();
    var datToday=new Date();

    intThisYear = sYear;
    intThisMonth = sMonth;
    intThisDay = sDay;

    if (intThisDay==0) intThisDay = datToday.getDay();
    if (intThisMonth==0) intThisMonth = datToday.getMonth();
    if (intThisYear==0) intThisYear = datToday.getYear();

    if (intThisMonth == 1)
    {
        intPrevYear=intThisYear-1;
        intPrevMonth=12;
        intNextYear=intThisYear;
        intNextMonth=2;
    }
    else if (intThisMonth==12)
    {
        intPrevYear=intThisYear;
        intPrevMonth=11;
        intNextYear=(parseInt(intThisYear) + 1);
        intNextMonth=1;
    }
    else
    {
        intPrevYear=intThisYear;
        intPrevMonth=intThisMonth -1;
        intNextYear=intThisYear;
        intNextMonth=Math.ceil(intThisMonth) + 1;
    }

    NowThisYear = sYear;
    NowThisMonth = sMonth;
    NowThisDay = sDay;

    if (NowThisDay==0) NowThisDay = datToday.getDay();
    if (NowThisMonth==0) NowThisMonth = datToday.getMonth();
    if (NowThisYear==0) NowThisYear = datToday.getYear();


    intFirstWeekday=BS_Weekday(intThisYear, intThisMonth);


    intSecondWeekDay=intFirstWeekday
    intThirdWeekDay=intFirstWeekday

    datThisDay= intThisYear.toString() +  "-" + intThisMonth.toString() + "-" + intThisDay.toString();
    intThisWeekday=BS_Weekday(intThisYear,intThisMonth);

    if (intThisWeekday == 1) varThisWeekday = "일";
    if (intThisWeekday == 2) varThisWeekday = "월";
    if (intThisWeekday == 3) varThisWeekday = "화";
    if (intThisWeekday == 4) varThisWeekday = "수";
    if (intThisWeekday == 5) varThisWeekday = "목";
    if (intThisWeekday == 6) varThisWeekday = "금";
    if (intThisWeekday == 7) varThisWeekday = "토";


    intPrintDay=1;
    secondPrintDay=1;
    thirdPrintDay=1;

    Stop_Flag=0;

    if (intThisMonth == 4 || intThisMonth==6 || intThisMonth==9 || intThisMonth==11)
    {
        intLastDay=30;
    }
    else if (intThisMonth==2 && !(intThisYear % 4 == 0))
    {
        intLastDay=28;
    }
    else if (intThisMonth==2 && intThisYear % 4 == 0)
    {
        if (intThisYear % 100 == 0)
        {
            if (intThisYear % 400 == 0)
                intLastDay=29;
            else
                intLastDay=28;
        }
        else
        {
            intLastDay=29;
        }
    }
    else
    {
        intLastDay=31;
    }

    if (intPrevMonth==4 || intPrevMonth==6 || intPrevMonth==9 || intPrevMonth==11)
        intPrevLastDay=30;
    else if (intPrevMonth==2 &&  !(intPrevYear % 4 == 0))
        intPrevLastDay=28;
    else if (intPrevMonth==2 && intPrevYear % 4 == 0)
    {
        if (intPrevYear % 100 == 0)
        {
            if (intPrevYear % 400 == 0)
                intPrevLastDay=29;
            else
                intPrevLastDay=28;
        }
        else
        {
            intPrevLastDay=29;
        }
    }
    else
    {
        intPrevLastDay=31;
    }


    Stop_Flag=0;
    Cal_HTML=Cal_HTML + "<table border='0' bgcolor='#DCDCDC' cellpadding=1 cellspacing=1  onmouseover='doOver()' onmouseout='doOut()' onclick='doClick_plan()' style='font-size : 12;font-family:굴림;'>";
    Cal_HTML=Cal_HTML + "<tr align=center>";
    Cal_HTML=Cal_HTML + "<td align=left  title='이전달' style='cursor:hand;' OnClick='JAVAScript:Show_plancal(" + intPrevYear.toString()  + "," + intPrevMonth.toString() + ",1)'><font color=006699 size=2>◀</font></td>";
    Cal_HTML=Cal_HTML + "<td colspan=5><font color=#990000><b>";
    Cal_HTML=Cal_HTML + intThisYear.toString() + "년 " + intThisMonth.toString() + "월";
    Cal_HTML=Cal_HTML + "</font></b></td>";
    Cal_HTML=Cal_HTML + "<td align=right title='다음달' style='cursor:hand;' OnClick='JAVAScript:Show_plancal(" + intNextYear.toString() + "," + intNextMonth.toString() + ",1)'><font color=006699 size=2>▶</font></a></td>";
    Cal_HTML=Cal_HTML + "</tr>";
    Cal_HTML=Cal_HTML + "<tr align=center bgcolor='8DCFF4' style='color:000000;'>";
    Cal_HTML=Cal_HTML + "<td>일</td><td>월</td><td>화</td><td>수</td><td>목</td><td>금</td><td>토</td>";
    Cal_HTML=Cal_HTML + "</tr>";

    for (intLoopWeek=1;intLoopWeek<=6;intLoopWeek++)
    {

        Cal_HTML=Cal_HTML + "<tr align=right valign=top bgcolor='#F6F9F3'>";
        for (intLoopDay=1;intLoopDay<=7;intLoopDay++)
        {
            if (intThirdWeekDay > 1)
            {
                //JIN
                Cal_HTML=Cal_HTML + "<td>&nbsp;</td>";
                intThirdWeekDay=intThirdWeekDay-1;
            }
            else
            {
                if (thirdPrintDay > intLastDay)
                {
                    //JIN
                    Cal_HTML=Cal_HTML + "<td>&nbsp;</td>";
                }
                else
                {
                    Cal_HTML=Cal_HTML + "<td title='" + intThisYear.toString() + "-" + intThisMonth.toString() + "-" + thirdPrintDay.toString() + "' style='cursor: hand;border: 1px solid white;width:18; height:18;";
                    if (intThisYear-NowThisYear==0 && intThisMonth-NowThisMonth==0 && thirdPrintDay-intThisDay==0)
                    {
                        Cal_HTML=Cal_HTML + "background-color:#FACD8A;";
                    }

                    if  (intLoopDay==1)
                    {
                        Cal_HTML=Cal_HTML + "color:#990000;";
                    }
                    else
                    {
                        Cal_HTML=Cal_HTML + "color:black;";
                    }

                    Cal_HTML=Cal_HTML+ "'>" + thirdPrintDay.toString();
                }
                thirdPrintDay++;

                if (thirdPrintDay > intLastDay) Stop_Flag=1;

            }
            Cal_HTML=Cal_HTML + "</td>";
        }
        Cal_HTML=Cal_HTML + "</tr>";
        if (Stop_Flag==1) break;
    }


    Cal_HTML=Cal_HTML+ "</table>";
    if(document.all.minical.length > 1){
        document.all.minical[0].innerHTML=Cal_HTML;
    }else{
        document.all.minical.innerHTML=Cal_HTML;
    }
}


function doClick_plan() {
    cal_Day = window.event.srcElement.title;
    window.event.srcElement.style.borderColor = "#990000";
    if (cal_Day.length > 7) {
        getFixed(cal_Day);
        //오늘날짜 이전이면 에러다
        var seldate = getYear()+''+getMonth()+''+getDay();
        var nowdate = s1+s2+s3;
        if(nowdate <= seldate){
            alert('오늘이후로 선택하셔야합니다.');
            return;
        }
        uf_newWin( '/kor/jsp/comm/comm_chkplandate.jsp?date='+s1+s2+s3, 'chkWin', '528', '241');
    }
}




/**
 *  반자를 전자로 변환
 */
function parseFull(HalfVal)
{
    var FullChar = [
                   "　", "！","＂","＃","＄","％","＆","＇","（",    //33~
            "）","＊","＋","，","－","．","／","０","１","２",      //41~
            "３","４","５","６","７","８","９","：","；","＜",      //51~
            "＝","＞","？","＠","Ａ","Ｂ","Ｃ","Ｄ","Ｅ","Ｆ",      //61~
            "Ｇ","Ｈ","Ｉ","Ｊ","Ｋ","Ｌ","Ｍ","Ｎ","Ｏ","Ｐ",      //71~
            "Ｑ","Ｒ","Ｓ","Ｔ","Ｕ","Ｖ","Ｗ","Ｘ","Ｙ","Ｚ",      //81~
            "［","￦","］","＾","＿","｀","Ａ","Ｂ","Ｃ","Ｄ",      //91~
            "Ｅ","Ｆ","Ｇ","Ｈ","Ｉ","Ｊ","Ｋ","Ｌ","Ｍ","Ｎ",      //101~
            "Ｏ","Ｐ","Ｑ","Ｒ","Ｓ","Ｔ","Ｕ","Ｖ","Ｗ","Ｘ",      //111~
            "Ｙ","Ｚ","｛","｜","｝","～"                         //121~
            ];
        var stFinal = "";
        var ascii;
        for( i = 0; i < HalfVal.length; i++)
        {
                ascii = HalfVal.charCodeAt(i);
                if( (31 < ascii && ascii < 128))
                {
                  stFinal += FullChar[ascii-32];
                }
                else
               {
                  stFinal += HalfVal.charAt(i);
                }
        }
        return stFinal;
}

/**
 *  전자를 반자로 변환
 */
function parseHalf(FullVal) {
    var HalfChar = [
            " ", "!","\"","#","$","%","&","'","(",
            ")","*","+",",","-",".","/","0","1","2",
            "3","4","5","6","7","8","9",":",";","<",
            "=",">","?","@","A","B","C","D","E","F",
            "G","H","I","J","K","L","M","N","O","P",
            "Q","R","S","T","U","V","W","X","Y","Z",
            "[","\\","]","^","_","`","a","b","c","d",
            "e","f","g","h","i","j","k","l","m","n",
            "o","p","q","r","s","t","u","v","w","x",
            "y","z","{","|","}","~"
            ];
    var stFinal = "";
    var ascii;

    for(var i = 0; i < FullVal.length; i++) {
        ascii = FullVal.charCodeAt(i);
        if (65280 < ascii && ascii < 65375) {
            stFinal += HalfChar[ascii - 65280];
        } else if (12288 == ascii) {
            stFinal += HalfChar[ascii - 12288];
        } else if (65510 == ascii) {
            stFinal += HalfChar[60];
        } else {
            stFinal += FullVal.charAt(i);
        }
    }
    return stFinal;
}


/**
 *  e-mail체크
 */
function isValidEmail( str ) {
    var aa = str.indexOf("@");
    var bb = str.indexOf(".");

    if ((str.length>0)&&((aa<0)||(bb<0)||(str.length<10))) {
        return false;
    } else {
        return true;
    }
}


/**
 *  e-mail체크
 */
function isValidEmail2(input) {
    if (input=="") {
        return true;
    }else{
//    var format = /^(\S+)@(\S+)\.([A-Za-z]+)$/;
    var format = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
    return isValidFormat(input,format);
  }
}


function uf_isValidEmail(input)
{
    //var format = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
    var format = /^((\w|[\-\.])+)\.([A-Za-z]+)$/;

alert(input.search(format));

    if (input.search(format) != -1) {
        return true; //올바른 포맷 형식
    } else {
        return false; //올바른 포멧 아님
    }
}

/**
 * 입력값이 사용자가 정의한 포맷 형식인지 체크
 * 자세한 format 형식은 자바스크립트의 'regular expression'을 참조
 */
function isValidFormat(input, format) {
    if (input.search(format) != -1) {
        return true; //올바른 포맷 형식
    }
    return false;
}

/**
 *  조회시작/종료일 기간 체크 (과거,현재,미래 상관없이 단순히 term만 체크)
 */
function checkTerm(yy1, mm1, dd1, yy2, mm2, dd2, term)
{
    var st_dt   = getDayString(yy1, mm1, dd1);
    var end_dt  = getDayString(yy2, mm2, dd2);
    var cal_dt  = shiftDate(st_dt, 0, term, 0);

    if(!isValidTerm(yy1, mm1, dd1, yy2, mm2, dd2))
    {
        return false;
    }
    else if(getSequence(end_dt, cal_dt) == -1)
    {
        alert("설정기간이 "+term+"개월을 초과했습니다");
        return false;
    }
    else
        return true;
}

/**
 *  isInRecentMonth : 최근일을 기준으로 기간 체크 ---> 미래일 조회 불가
 *  checkTerm       : 단순히 term 만 체크
 */

function checkTerm(yy1, mm1, dd1, yy2, mm2, dd2, term)
{
    var st_dt   = getDayString(yy1, mm1, dd1);
    var end_dt  = getDayString(yy2, mm2, dd2);
    var cal_dt  = shiftDate(st_dt, 0, term, 0);

    if(!isValidTerm(yy1, mm1, dd1, yy2, mm2, dd2))
    {
        return false;
    }
    else if(getSequence(end_dt, cal_dt) == -1)
    {
        alert("설정기간이 "+term+"개월을 초과했습니다");
        return false;
    }
    else
        return true;
}

    function cutContent(obj){

        if(obj.length > 30){

            obj = obj.substring(0,30)+"...";
        }

        return obj;
    }

/**
 *	숫자체크 및 컴마를 체크해준다.
 *	사용법 <input type=text name=money onkeydown='moneyBlur(this)'>
 */
function moneyBlur(obj)
{
    var strValue = obj.value;

    if(strValue=="") return;

    strValue = removeComma(strValue);

    if(event.keyCode<45 || event.keyCode>57) {
        event.returnValue=false;
    } else {
        obj.value = toMoney(strValue);
    }
}

/////////// 돈계산 //////////
function toMoney(Source)
{
    var     strSource = Source;
    var     strDestination = "";
    var     i;

    for (i = 0; i < strSource.length; i++) {

        if (strSource.charAt(i) != ' ') {
            strDestination += strSource.charAt(i);

            if (((strSource.length - i) % 3) == 0) {
                strDestination  += ',';
            } 
        }
    }

    if(strDestination == 0){
        strDestination = "";
    }

    return strDestination ;
}

//숫자안에 컴마를 제거한다.
function removeComma(strValue){

    var sortValue = "";

    if(strValue.indexOf(",") != -1){

        for(var j=0;j < strValue.length;j++){

            if(strValue.charAt(j) != ","){
                sortValue += strValue.charAt(j);
            }
        }
    }else{
        sortValue = strValue;
    }

    if(sortValue == ""){
        sortValue = 0;
    }
    return sortValue;
}

function removeZero(cnt){

    var returnCnt;
    if(cnt == 0){
        returnCnt = "";
    }

    return returnCnt;
}

/***보험연령계산***/
function InsuAge(jumin){
    var today;     //현재일자
    var Yinterval; //연차이
    var Minterval; //월차이
    var Dinterval; //일차이
    var insuage;  //보험 연령
    today = new Date();

    //성진수정 2003.07.09
    if(jumin.length < 7) return;

    Yinterval = Math.abs( today.getYear()-birthyear(jumin)); //현재일자와 생년월일 사이의 년수를 구함
    Minterval =  (today.getMonth()+1)- birthmonth(jumin);       //현재일자와 생년월일 사이의 달수를 구함
    Dinterval =  today.getDate()-birthdate(jumin);                  //현재일자와 생년월일 사이의 일수를 구함
    if( Minterval > 6){
        insuage  = Yinterval + 1;
    }
    else if(Minterval == 6 ){
        if (Dinterval>=0){
            insuage = Yinterval + 1;
        }else {
            insuage = Yinterval;
        }
    }
    else if(Minterval< -6){
        insuage  = Yinterval - 1;
    }
    else if(Minterval == -6){
        if (Dinterval<0){
            insuage = Yinterval - 1;
        }else {
            insuage = Yinterval;
        }
    }
    else{
        insuage = Yinterval;
    }
    return  insuage;
 }

 /***고객 출생연  산출***/
 function  birthyear(jumin){
    var year;

    if ((jumin.substring(6,7)=="1") ||(jumin.substring(6,7)=="2"))
    {
       year = ("19"+jumin.substring(0,2));
    }
    if ((jumin.substring(6,7)=="3") ||(jumin.substring(6,7)=="4"))
    {
        year = ("20"+jumin.substring(0,2));
    }
    return year;
}

/***고객 출생월  산출***/
function  birthmonth(jumin){
    var month ;
    month=jumin.substring(2,4);
    return month;
 }

/***고객 출생일  산출***/
function  birthdate(jumin){
    var date;
    date=jumin.substring(4,6);
    return date;
}

/**
 * 유효한(존재하는) 월(月)인지 체크
 */
function validMonth(mm)
{
    if(mm.value != ""){
        var m = parseInt(mm.value,10);
        if(m >= 1 && m <= 12){
            if(m < 10) m = "0"+m;
            mm.value = m;
        }else{
            alert("유효하지 않은 월입니다.");
            mm.focus();
        }
    }
}

/**
 * 유효한(존재하는) 일(日)인지 체크
 */
function validDay(yyyy, mm, dd) {

    if(dd.value != ""){
        var m = parseInt(mm,10) - 1;
        var d = parseInt(dd.value,10);

        var end = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
        if ((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0) {
            end[1] = 29;
        }

        if(d >= 1 && d <= end[m]){

            if(d < 10) d = "0"+d;
            dd.value = d;

        }else{
            alert("유효하지 않은 일입니다.");
            dd.focus();
        }
    }
}

/**
 * 유효한(존재하는) 시(時)인지 체크
 */
function validHour(hh) {

    if(hh.value != ""){
        var h = parseInt(hh.value,10);

        if(h >= 1 && h <= 24){

            if(h < 10) h = "0"+h;
            hh.value = h;

        }else{
            alert("유효하지 않은 시간입니다.");
            hh.focus();
        }
    }
}

/**
 * 유효한(존재하는) 분(分)인지 체크
 */
function validMin(mi) {

    if(mi.value != ""){
        var m = parseInt(mi.value,10);

        if(m >= 0 && m < 60){
            if(m < 10) m = "0"+m;
            mi.value = m;
        }else{
            alert("유효하지 않은 분입니다.");
            mi.focus();
        }
    }
}

/**
 * 상령일 가져오기
 */
function SangDay(jumin)
{
    if(jumin.length != 13) return;
    var Sex  = jumin.substring(6,7);
    var Year = jumin.substring(0,2);
    var Mon  = jumin.substring(2,4);
    var Day  = jumin.substring(4,6);

    if     (Sex=='1'||Sex=='2'){ Year = '19' + Year; }
    else if(Sex=='3'||Sex=='4'){ Year = '20' + Year; }

    var today    = new Date();
    var sangYear = today.getYear();
    var SangDay  = "";

    if (Mon.substr(0,1) == 0) { Mon = Mon.substring(1,2); }
    if (Day.substr(0,1) == 0) { Day = Day.substring(1,2); }

    var sangMon  = parseInt(Mon) + 6;
    var sangDay  = parseInt(Day);

     if(sangMon > 12 )
     {
         sangMon  = parseInt(sangMon) - 12;
         sangYear = parseInt(sangYear) + 1;
     }

      //윤년인지 판단 (2월)
      if(sangYear%4 != 0 && sangMon == 2 && sangDay > 28)
      {
          sangMon = "03";
          sangDay = "01";
      }

      if (("" + sangMon).length == 1)   { sangMon  = "0" + sangMon;  }
      if (("" + sangDay).length == 1)   { sangDay  = "0" + sangDay;  }

      SangDay = "" + sangYear + sangMon + sangDay;

      return SangDay;
}

//"-"의 값을 ""로 바꿔준다.
function changeSpace(obj){

    var returnChange = "";
    var pluse = "";
    var cnt = obj.toString();

    for(var i=0; i < cnt.length;i++){

        if(cnt.charAt(i) == "-"){
            pluse = "";
        }else{
            pluse = cnt.charAt(i);
        }
        returnChange += pluse;
    }
    return returnChange;
}


/**
 * 입력값이 특정 문자(chars)만으로 되어있는지 체크
 * 특정 문자만 허용하려 할 때 사용
 * ex) if (!hasCharsOnly(form.blood,"ABO")) {
 *         alert("혈액형 필드에는 A,B,O 문자만 사용할 수 있습니다.");
 *     }
 */
function hasCharsOnly1(input, chars) {
    for (var inx = 0; inx < input.length; inx++) {
       if (chars.indexOf(input.charAt(inx)) == -1)
           return false;
    }

    return true;
}

//*************************************************************************************************
// DDD지역번호 유효성 체크
// 인자 : 트림된 스트링
// 리턴값 : boolen
//*************************************************************************************************
function DDD(val){
    if(val == "02" || val == "031"  || val == "032"  || val == "033"  || val == "041"  || val == "042"  || val == "043"  || val == "051"  || val == "052"  || val == "053"  || val == "054"  ||
       val == "055"  || val == "061"  || val == "062" || val == "063"  || val == "064" )
    {
        return true;
    }
    else
    {
        return false;
    }
}

function HDDD(val)
{
    if(val == "011" || val == "016"  || val == "017"  || val == "018"  || val == "019")
    {
        return true;
    }
    else
    {
        return false;
    }
}

//주민번호 앞자리 체크
function check_day(obj,year, month, day){

    var toDay = year + month + day;
    var temp_day;

    if (month==1  || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){

        temp_day=31;

    }else if (month==4 || month==6 || month==9 || month==11){

        temp_day=30;

    }else if (month==2){

        if (((year % 4)==0) && ((year % 100)!=0) || ((year % 400)==0)){
            temp_day=29;
        }else{
            temp_day=28;
        }
    }

    if(month > 12 || temp_day   < day){

        alert("주민번호가 잘못되었습니다.");
        obj.focus();
        return false;
    }
}

/**
 *  보험나이 자동세팅
 */
function ageValue(jumin)
{
    var birday  = jumin;
    var byear   = birday.substring(0,2);
    var bmon    = birday.substring(2,4);
    var bday    = birday.substring(4,6);
    var gubun   = birday.substring(6,7);

    var today = new Date();
    var tyear = today.getYear();
    var tmon  = today.getMonth()+1;
    var tday  = today.getDate();
    var boAge = "";

    if(birday.length == 0)
    {
        boAge = "";
    }
    else if(birday.substring(0,6) == '000000')
    {
        boAge = 0;
    }
    else
    {
        if     (gubun == '1' || gubun == '2'){ byear = '19' + byear; }
        else if(gubun == '3' || gubun == '4'){ byear = '20' + byear; }

        if(bmon.length == 2 && bmon.substr(0,1) == "0") { bmon = bmon.substr(1,1); }
        if(bday.length == 2 && bday.substr(0,1) == "0") { bday = bday.substr(1,1); }

        var inday = getLastday(tyear,tmon);
        var biday = getLastday(byear,bmon);

        if((parseInt(tmon) - parseInt(bmon)) > 6)
        {
            boAge = (parseInt(tyear) - parseInt(byear)) + 1;
        }
        else if((parseInt(tmon) - parseInt(bmon)) == 6)
        {
            if(parseInt(tday) < parseInt(bday))
            {
                boAge = parseInt(tyear) - parseInt(byear);

                if(parseInt(inday) == parseInt(tday))
                {
                    if(parseInt(biday) == parseInt(bday)) boAge = (parseInt(tyear) - parseInt(byear)) + 1;
                }
            }
            else
            {
                boAge = (parseInt(tyear) - parseInt(byear)) + 1;
            }
        }
        else if((parseInt(tmon) - parseInt(bmon)) == -6)
        {
            if(parseInt(tday) < parseInt(bday))
            {
                boAge = (parseInt(tyear) - parseInt(byear)) - 1;

                if(parseInt(inday) == parseInt(tday))
                {
                    if(parseInt(biday) == parseInt(bday)) boAge = (parseInt(tyear) - parseInt(byear)) + 1;
                }
            }
            else
            {
                boAge = parseInt(tyear) - parseInt(byear);
            }
        }
        else if((parseInt(tmon) - parseInt(bmon)) < -6)
        {
            boAge = (parseInt(tyear) - parseInt(byear)) - 1;
        }
        else
        {
            boAge = parseInt(tyear) - parseInt(byear);
        }
    }
    return boAge;
}


// 숫자인지 확인하는 메소드
function isNum(value) {
    var result = true;

    for(j = 0; result && (j < value.length); j++) {

        if((value.substring(j, j+1) < "0") || (value.substring(j, j+1) > "9")) {
            result = false;
        }
    }

    return result;
}

//숫자여부확인 ONBLUR()
function checkNumber(obj){
    if(!isNum(obj.value)){
        alert("숫자로만 이루어진 항목입니다.");
        obj.value = "";
        //obj.focus();
        return false;
    }

    return true;
}

// 숫자만 입력 가능하게 해준다....
function checkNum() {
    var key = String.fromCharCode(event.keyCode);
    var re = new RegExp('[0-9]');

    if(!re.test(key)) {
        event.returnValue = false;
    }
}


// 빈값처리
// strReplacement = 대체할 값
function nvl(strSrc, strReplacement) {

    var nvl = "";
    if(strSrc == "" || strSrc == null || strSrc=="null"){
        nvl = strReplacement;

    }else{
        strSrc = trim(strSrc)

        if(strSrc == ""){
            nvl = strReplacement;

        }else{
            nvl = strSrc;

        }
    }

    return nvl;
}


// "$" 와 "," 입력 제거
// 홍치화
function filterNum(strSrc) {

    reg = /^$|,/g;
    return strSrc.replace(reg, "");
}

// 입력된 돈값(ex)1,000)을 계산식으로
// 홍치화
function getMoney(strSrc) {

    var newVal = 0;

    if(strSrc == null || strSrc == ""){
        strSrc = "0";
    }

    newVal = filterNum(strSrc);
    return newVal;
}


//퍼센트를 넣으면 값을 반환
//frm=적용될 인풋박스이름, val1=기준값(월생활비), val2=설정퍼센트

/*사용법
function b_onBlur() {
    f_rdOnblur(this);
    transPer(document.form1.familyLivingMoney,document.form1.livingSum.value,this.value);
}
*/
function transPer(frm,val1,val2){

    val1 = filterNum(val1);
    val2 = filterNum(val2);

    rtnVal = Math.round((val1 / 100) * val2);

    frm.value=rtnVal;
}



// 입력된 돈을 인자값에 따른 화폐단위로
// 기본돈값 은 원단위
// 홍치화
function convertCurrency(strSrc,cVal){

    var newVal = 0;
    var currency = 1;

    if (strSrc == null || strSrc == ""){
        strSrc = 0;
    }

    if (cVal == null || cVal == ""){
        cVal = 1;
    }

    currency = cVal;
    newVal = Math.round(strSrc / currency);
    newVal = eval(getMoney(String(newVal)));

    return newVal;
}




//==============================================================================
// CHECKBOX 전체선택 / 해제 관련
//==============================================================================
//전체 checkbox의 갯수를 구한다.
//사용예 : getCheckboxCount( document.form1.menu )
function getCheckboxCount( obj ) {
    return eval(obj).length;
}

//선택된 checkbox의 갯수를 리턴한다.
function getCheckedCount( obj )
{
    var chkboxCnt = getCheckboxCount( obj );
    var cnt = 0;

    for (var j=0; j<chkboxCnt; j++)
    {
        if( obj[j].type=="checkbox" && obj[j].checked ) cnt++;
    }

    return cnt;
}

//선택된 checkbox의 value를 구분자로 구분하여 리턴한다.
//사용예 : getCheckValue( document.form1.menu, "|" )
function getCheckValue( obj, deli ) {

    var chkboxCnt = getCheckboxCount( obj );
    var val = "";

    deli = nvl(deli, ",");

    for (var j=0; j<chkboxCnt; j++)
    {
        if( obj[j].type=="checkbox" && obj[j].checked ) {
            val += obj[j].value + deli;
        }
    }

    if(chkboxCnt>0) {
        val = val.substr(0, val.length-1);
    }

    return val;
}

//form안에 들어있는 CheckBox의 상태를 모두 Checked 되게 한다.
//사용법 onclick="checkAll( document.form1.chk );"
function checkAll( obj ) {
    var chkboxCnt = getCheckboxCount( obj );

    if(getCheckedCount(obj) > 0) {
        unCheckAll( obj );
    } else {
        for (var j=0 ; j<chkboxCnt ; j++) {
            obj[j].checked = true;
        }    
    }
}

//form안에 들어있는 CheckBox의 상태를 모두 unChecked 되게 한다.
function unCheckAll( obj )
{
    var chkboxCnt = getCheckboxCount( obj );

    for (var j=0 ; j<chkboxCnt ; j++) {
        obj[j].checked = false;
    }
}

/**
 * checkbox를 통해 전체선택 또는 해제를 처리하는 경우
 *
 * [사용법]
 * doCheck(document.form1, document.form1.chk) 또는
 * doCheck(document.form1, document.form1.chk, document.form1.chkall) 또는
 */
function doCheck( obj, objName, checkerObjName )
{
    objName        = (objName==undefined||objName=="") ? obj.chk : objName;
    checkerObjName = (checkerObjName==undefined||checkerObjName=="") ? obj.chkall : checkerObjName;

    if(checkerObjName.checked)
        checkAll( objName );
    else
        unCheckAll( objName );

}

/**
 * 이미지를 클릭하여 전체선택 또는 해제를 처리하는 경우
 *
 * [사용법]
 * doCheck(document.form1, document.form1.chk) 또는
 * doCheck(document.form1, document.form1.chk, "chkImage") 또는
 */
function doCheck2( obj, objName, checkerImgId )
{
    objName      = (objName==undefined||objName=="") ? obj.chk : objName;
    checkerImgId = (checkerImgId==undefined||checkerImgId=="") ? document.all.chkImage : eval("document.all."+checkerImgId);

    if(checkerImgId.check=="false") {
        checkAll( objName );
        checkerImgId.check="true";
    } else {
        unCheckAll( objName );
        checkerImgId.check="false";
    }
}



//==============================================================================
// form의 엘리먼트 값을 읽어와 get 방식의 문자열로 만든다.
// ajax 사용시 인자로 넘겨줄 parameter 값을 얻기 위해 만들어진 함수
//==============================================================================
function makeParam(objname) {

    var frm    = eval("document."+objname);
    var result = "";

    for(i=0 ; i<frm.length ; i++) {
        var obj     = frm.item(i);
        var objType = obj.type;
        var objName = obj.name;
        var objVal  = obj.value;

        if(objName!="") {

            if(objType=="radio" && obj.checked) {
                result += "&" + objName + "=" + objVal;
            }

            if(objType=="checkbox" && obj.checked) {
                result += "&" + objName + "=" + objVal;
            }

            if(objType=="text") {
                result += "&" + objName + "=" + objVal;
            }

            if(objType=="password") {
                result += "&" + objName + "=" + objVal;
            }

            if(objType=="hidden") {
                result += "&" + objName + "=" + objVal;
            }

            if(objType=="textarea") {
                result += "&" + objName + "=" + objVal;
            }

            if(objType.indexOf("select")==0) {
                result += "&" + objName + "=" + objVal;
            }

        }
    }

    result = result.substr(1, result.length);

    return result;
}


//==============================================================================
// 주민번호 유효성 검사
//
// - 사용법
// if( !checkJuminNo(document.form1.jumin1, document.form1.jumin2) ) {
//     return;
// }
//==============================================================================
function checkJuminNo(frm1, frm2) {

    var iden = frm1.value + frm2.value;

    if(frm1.value=="")
    {
        alert("주민등록번호를 입력하여 주십시오.");
        frm1.focus();
        return false;
    }

    if(frm2.value=="")
    {
        alert("주민등록번호를 입력하여 주십시오.");
        frm2.focus();
        return false;
    }

    if(iden.length != 13) {
        alert("정상적인 주민등록번호가 아닙니다.");
        frm1.value="";
        frm2.value="";
        frm1.focus();
        return false;
    }

    var iden_tot = 0;
    var iden_ad = "234567892345";

    for(i=0; i<=11; i++) {
        iden_tot = iden_tot + parseInt(iden.substring(i, i+1))*parseInt(iden_ad.substring(i, i+1));
    }

    iden_tot = 11 - (iden_tot % 11);

    if(iden_tot == 10) iden_tot = 0;
    else if(iden_tot == 11) iden_tot = 1;

    if(parseInt(iden.substring(12, 13)) != iden_tot) {
        alert("정상적인 주민등록번호가 아닙니다.");
        frm1.focus();
        return false;
    }
    else
    {
        return true;
    }
}


//==============================================================================
// 입력된 글자의 바이트수를 계산하여 해당 바이트 초과시 글자를 잘라서 보여준다.
//
// - 사용법
// onKeyUp="textCounter(1000, document.form1.comment);"
//==============================================================================
//쪽지 글자수 체크
function textCounter( maxlen, objName )
{
    var tlen = checkByte( objName.value );

    if( tlen > maxlen )
    {
        alert("글자수가 초과되었습니다. "+maxlen+"자 까지만 입력하실 수 있습니다.") ;
        cutChar( maxlen, objName );
    }

    document.all.clock.innerHTML = "(" + tlen + " Byte 입력)" ;
}

/* 전송하고자 하는 메세지의 바이트수를 체크한다. */
function checkByte( val )
{
    var str, msg;
    var len = 0;
    var temp;
    var count = 0;

    msg = val; //frm.content.value;

    str = new String(msg);

    len = str.length;

    for (k=0 ; k<len ; k++)
    {
        temp = str.charAt(k);

        if (escape(temp).length > 4)
        {
            count += 2;
        }
        else if (temp == '\r' && str.charAt(k+1) == '\n')
        {
            count += 2;
        }
        else if (temp != '\n')
        {
            count++;
        }
    }

    return count;
}

/* 지정된 바이트가 초과경우 문자를 삭제한다. */
function cutChar( maxlen, obj )
{
    var str, msg;
    var len = 0;
    var temp;
    var count;
    count = 0;

    msg = obj.value; //frm.content.value;
    str = new String(msg);
    len = str.length;

    for(k=0 ; k<len ; k++)
    {
        temp = str.charAt(k);

        if(escape(temp).length > 4)
        {
            count += 2;
        }
        else if(temp == '\r' && str.charAt(k+1) == '\n')
        {
            count += 2;
        }
        else if(temp != '\n')
        {
            count++;
        }

        if(count > Number(maxlen))
        {
            str = str.substring(0, k);
            break;
        }
    }

    obj.value = str;
}



//==============================================================================
// 쿠키 설정
//
// - 사용법
//   setCookie( "Notice", "check" , 1)
//==============================================================================
// 쿠키 생성
function setCookie( name, value, expiredays ) {

    var todayDate = new Date();

    todayDate.setDate( todayDate.getDate() + expiredays );

    document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
}

// 저장된 쿠키를 가져온다.
function getCookie( name ) {

    var nameOfCookie = name + "=";
    var x = 0;

    while ( x <= document.cookie.length ) {

        var y = (x+nameOfCookie.length);

        if ( document.cookie.substring( x, y ) == nameOfCookie ) {
            if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                    endOfCookie = document.cookie.length;
            return unescape( document.cookie.substring( y, endOfCookie ) );
        }

        x = document.cookie.indexOf( " ", x ) + 1;

        if ( x == 0 ) break;
    }

    return "";
}


//페이지 이동 처리
function goPage( pageNo, uri ) {

    var frm;
    var element;

    uri = nvl(uri, "?");

    if( j$("form1") ) {
    
        if(document.form1.pageNo) {
            document.form1.pageNo.value = pageNo;
        } else {
            element = "<input type=hidden id=pageNo name=pageNo value='"+pageNo+"'>";

            Element.insert("form1", element);
        }

    } else {
        element += "<form method=post id=form1 name=form1 action='"+uri+"'>";
        element += "<input type=hidden id=pageNo name=pageNo value='"+pageNo+"'>";
        element += "</form>";

        Element.insert("physical_body", element);
    }


//    if(document.form1!=null && document.form1!="undefined") {
//        frm = document.form1;
//        frm.pageNo.value = pageNo;
//
//        doSubmitPost(uri, frm, "");
//    } else {
//        alert("전송할 폼 객체가 존재하지 않습니다.");
//    }
//
//    cfGoto( uri+"&page="+pageNo );
}



//------------------------------------------------------------------------------
// 공통 submit 처리
//------------------------------------------------------------------------------

//form을 post방식으로 submit한다. (이동할URL, 전송할form, 타겟 frame)
//사용법 : doSubmit("/app/sample/selectSampleList", form1, "hidden_iframe", false)
function doSubmit(uri, submitForm, targetName, isDebug) {
	
	
    if(isDebug==false) {
        if(uri.indexOf("insert")>-1 || uri.indexOf("update")>-1 || uri.indexOf("delete")>-1) {
            //ui block 설정
            //callUiBlock("1");    
        }    
    }

    doSubmitPost(uri, submitForm, targetName, isDebug);
}

//doSubmit과 동일하나 자식창에서 부모창을 호출할 경우에만 사용한다.
function doSubmit2(uri, submitForm, targetName, isDebug)
{
    doSubmitPost(uri, submitForm, targetName, isDebug);
}

//form을 get방식으로 submit한다. (이동할URL, 전송할form, 타겟 frame)
//사용법 : doSubmitGet("/member.jsp", form1, "hidden_iframe")
function doSubmitGet(uri, submitForm, targetName, isDebug)
{
    doSubmitBase(uri, submitForm, targetName, "get", "", isDebug);
}

//form을 post방식으로 submit한다. (이동할URL, 전송할form, 타겟 frame)
//사용법 : doSubmitGet("/member.jsp", form1, "hidden_iframe")
function doSubmitPost(uri, submitForm, targetName, isDebug)
{
    doSubmitBase(uri, submitForm, targetName, "post", "", isDebug);
}

//form을 submit한다. (이동할URL, 전송할form, 타겟 frame, disable시킬 버튼ID)
function doSubmitBase(uri, submitForm, targetName, method, btnName, isDebug)
{
    //변수가 정의되지 않았을 경우의 처리
    if(uri       =="undefined" || uri       ==undefined) uri        = "";
    if(submitForm=="undefined" || submitForm==undefined) submitForm = "";
    if(targetName=="undefined" || targetName==undefined) targetName = "";
    if(method    =="undefined" || method    ==undefined) method     = "POST";

    //submit시 버튼 disable 시키기
    if( btnName!=undefined
        && btnName!="" ) {

        //var obj = eval("document.all."+btnName);

        //obj.disabled = true;
        //obj.style.display = "none";

        j$(btnName).hide();
    }


    if(submitForm=="") {
        if(targetName!="") {
            eval(targetName).location.replace(uri);
        } else {
            location.replace(uri);
        }

    } else {
        submitForm.target = targetName;
        submitForm.action = uri;
        submitForm.method = method;
    }

    if(isDebug) {

        log("[uri] : " + uri);
        log("[submitForm] : " + submitForm);

        var param = "[parameter] : ";
        param += j$(submitForm).serialize();
        param += "<input type=button value=send onclick=\"doSubmitBase('"+uri+"', "+submitForm.name+", '"+targetName+"', '"+method+"', '"+btnName+"', false);\"/>"

        log(param);
    } else {
        submitForm.submit();
    }

    return;
}


//------------------------------------------------------------------------------
//FCKEditor의 글 내용을 리턴
//------------------------------------------------------------------------------
function getFckContents(objName) {

    if(arguments.length==0) {
        objName = "contents_nec";
    }

    var oFCKeditor = FCKeditorAPI.GetInstance( objName );
    var contents = oFCKeditor.GetXHTML(false);

    if(contents.length>0) {
        contents = contents.replace("'", "\'");
        contents = contents.replace("\"", "\\\"");
    }

    return contents;
}

//------------------------------------------------------------------------------
//FCKEditor의 글 내용 삽입
//------------------------------------------------------------------------------
function setFckContents( str, objName ) {

    if(arguments.length==1) {
        objName = "contents_nec";
    }

    var oFCKeditor = FCKeditorAPI.GetInstance( objName );
    oFCKeditor.SetHTML( str );
}


//------------------------------------------------------------------------------
// ajax 처리(jQuery 모듈 이용)
//------------------------------------------------------------------------------
function doAjax(uri, data, callback, isDebug) {

    if(isDebug) {
        log("[ajax parameter] :" + data);
    }

    j$.ajax({
        type     : "POST",
        url      : uri,
        data     : data,
        success  : function(msg) {
            var result = eval( "(" + msg + ")" );
            if(isDebug) log("[ajax result] :" + msg);

			//예외가 발생할 경우에는 바로 예외처리
			if(result['errorCode']!=null) {
				var errorCode = result['errorCode'];
				
				alert(result['errorMessage']);
				
				if(errorCode=='NO_SESSION' || errorCode=='NO_LOGIN') {
					top.location.href = '/app/auth/loginForm';
				}
				
			} else {
	            eval(callback)( result );
			}

        },
		error : function() {
			alert("처리 중 오류가 발생하였습니다.");
		}
    });
}

function go(page) {
    var frm = document.form1;

    frm.page.value=page;
    frm.target = "";
    frm.submit();
}
function goTo(link) {
    window.location.href = link;
}
function goPage(asp_file_name,dbname,block,page,ck01,ck02,ck03,ck04,ck05,ck06,ck07,ck08,ck09,ck10,ck11,ck12,ck13,ck14,ck15,ck16,ck17,ck18,ck19,ck20,pageListView) {
	window.location.href = asp_file_name + "_list.asp?dbname="+dbname+"&block="+block+"&page="+page+"&ck01="+escape(ck01)+"&ck02="+ck02+"&ck03="+escape(ck03)+"&ck04="+ck04+"&ck05="+ck05+"&ck06="+ck06+"&ck07="+ck07+"&ck08="+escape(ck08)+"&ck09="+ck09+"&ck10="+ck10+"&ck11="+ck11+"&ck12="+ck12+"&ck13="+encodeURIComponent(ck13)+"&ck14="+ck14+"&ck15="+ck15+"&ck16="+ck16+"&ck17="+ck17+"&ck18="+ck18+"&ck19="+ck19+"&ck20="+ck20+"&pageListView="+pageListView;
}
function goPageView(asp_file_name,dbname,idx,block,page,ck01,ck02,ck03,ck04,ck05,ck06,ck07,ck08,ck09,ck10,ck11,ck12,ck13,ck14,ck15,ck16,ck17,ck18,ck19,ck20,pageListView) {
	window.location.href = asp_file_name + "_view.asp?dbname="+dbname+"&idx="+idx+"&block="+block+"&page="+page+"&ck01="+ck01+"&ck02="+ck02+"&ck03="+escape(ck03)+"&ck04="+ck04+"&ck05="+ck05+"&ck06="+ck06+"&ck07="+ck07+"&ck08="+ck08+"&ck09="+ck09+"&ck10="+ck10+"&ck11="+ck11+"&ck12="+ck12+"&ck13="+encodeURIComponent(ck13)+"&ck14="+ck14+"&ck15="+ck15+"&ck16="+ck16+"&ck17="+ck17+"&ck18="+ck18+"&ck19="+ck19+"&ck20="+ck20+"&pageListView="+pageListView;
}
function goPageEdit(asp_file_name,dbname,idx,block,page,ck01,ck02,ck03,ck04,ck05,ck06,ck07,ck08,ck09,ck10,ck11,ck12,ck13,ck14,ck15,ck16,ck17,ck18,ck19,ck20,pageListView) {
	window.location.href = asp_file_name + "_edit.asp?dbname="+dbname+"&idx="+idx+"&block="+block+"&page="+page+"&ck01="+ck01+"&ck02="+ck02+"&ck03="+escape(ck03)+"&ck04="+ck04+"&ck05="+ck05+"&ck06="+ck06+"&ck07="+ck07+"&ck08="+ck08+"&ck09="+ck09+"&ck10="+ck10+"&ck11="+ck11+"&ck12="+ck12+"&ck13="+encodeURIComponent(ck13)+"&ck14="+ck14+"&ck15="+ck15+"&ck16="+ck16+"&ck17="+ck17+"&ck18="+ck18+"&ck19="+ck19+"&ck20="+ck20+"&pageListView="+pageListView;
}
function goPageEdit1(asp_file_name,dbname,idx,block,page,ck01,ck02,ck03,ck04,ck05,ck06,ck07,ck08,ck09,ck10,ck11,ck12,ck13,ck14,ck15,ck16,ck17,ck18,ck19,ck20,ck21,ck22,ck23,ck24,ck25,ck26,ck27,ck28,ck29,ck30,pageListView) {
	window.location.href = asp_file_name + "_edit.asp?dbname="+dbname+"&idx="+idx+"&block="+block+"&page="+page+"&ck01="+ck01+"&ck02="+ck02+"&ck03="+escape(ck03)+"&ck04="+ck04+"&ck05="+ck05+"&ck06="+ck06+"&ck07="+ck07+"&ck08="+ck08+"&ck09="+ck09+"&ck10="+ck10+"&ck11="+ck11+"&ck12="+ck12+"&ck13="+encodeURIComponent(ck13)+"&ck14="+ck14+"&ck15="+ck15+"&ck16="+ck16+"&ck17="+ck17+"&ck18="+ck18+"&ck19="+ck19+"&ck20="+ck20+"&ck21="+ck21+"&ck22="+ck22+"&ck23="+ck23+"&ck24="+ck24+"&ck25="+ck25+"&ck26="+ck26+"&ck27="+ck27+"&ck28="+ck28+"&ck29="+ck29+"&ck30="+ck30+"&pageListView="+pageListView;
}



function goPage1(asp_file_name,dbname,block,page,ck01,ck02,ck03,ck04,ck05,ck06,ck07,ck08,ck09,ck10,ck11,ck12,ck13,ck14,ck15,ck16,ck17,ck18,ck19,ck20,ck21,ck22,ck23,ck24,ck25,ck26,ck27,ck28,ck29,ck30,pageListView) {
	window.location.href = asp_file_name + "_list.asp?dbname="+dbname+"&block="+block+"&page="+page+"&ck01="+escape(ck01)+"&ck02="+ck02+"&ck03="+escape(ck03)+"&ck04="+ck04+"&ck05="+ck05+"&ck06="+ck06+"&ck07="+ck07+"&ck08="+escape(ck08)+"&ck09="+ck09+"&ck10="+ck10+"&ck11="+ck11+"&ck12="+ck12+"&ck13="+encodeURIComponent(ck13)+"&ck14="+ck14+"&ck15="+ck15+"&ck16="+ck16+"&ck17="+ck17+"&ck18="+ck18+"&ck19="+ck19+"&ck20="+ck20+"&ck21="+ck21+"&ck22="+ck22+"&ck23="+ck23+"&ck24="+ck24+"&ck25="+ck25+"&ck26="+ck26+"&ck27="+ck27+"&ck28="+ck28+"&ck29="+ck29+"&ck30="+ck30+"&pageListView="+pageListView;
}

function goPageView1(asp_file_name,dbname,idx,block,page,ck01,ck02,ck03,ck04,ck05,ck06,ck07,ck08,ck09,ck10,ck11,ck12,ck13,ck14,ck15,ck16,ck17,ck18,ck19,ck20,ck21,ck22,ck23,ck24,ck25,ck26,ck27,ck28,ck29,ck30,pageListView) {
	window.location.href = asp_file_name + "_view.asp?dbname="+dbname+"&idx="+idx+"&block="+block+"&page="+page+"&ck01="+ck01+"&ck02="+ck02+"&ck03="+escape(ck03)+"&ck04="+ck04+"&ck05="+ck05+"&ck06="+ck06+"&ck07="+ck07+"&ck08="+ck08+"&ck09="+ck09+"&ck10="+ck10+"&ck11="+ck11+"&ck12="+ck12+"&ck13="+encodeURIComponent(ck13)+"&ck14="+ck14+"&ck15="+ck15+"&ck16="+ck16+"&ck17="+ck17+"&ck18="+ck18+"&ck19="+ck19+"&ck20="+ck20+"&ck21="+ck21+"&ck22="+ck22+"&ck23="+ck23+"&ck24="+ck24+"&ck25="+ck25+"&ck26="+ck26+"&ck27="+ck27+"&ck28="+ck28+"&ck29="+ck29+"&ck30="+ck30+"&pageListView="+pageListView;
}