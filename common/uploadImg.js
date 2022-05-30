const uploadImg = document.getElementById("uploadImg");

if(uploadImg !== null){
	uploadImg.addEventListener('change', function(){
		if (this.files.length > 0) {
			// 選択されたファイル情報を取得
			var file = this.files[0];

			// readerのresultプロパティに、データURLとしてエンコードされたファイルデータを格納
			var reader = new FileReader();
			reader.readAsDataURL(file);

			reader.onload = function() {
				$('#thumbnail').attr('src', reader.result);
				$('#icon').attr('value', reader.result.split(',')[1]);
			}

		}
	});
}