function dropDown(bodynum, id){

	// html要素の取得
	const btn = document.getElementById('dropdown_btn_' + bodynum + '_' + id);
	btn.classList.toggle('is-open');

}