
let fav_bool_js = {};
let follow_bool_js = {};
var userid_js = "";

function changeFav(id, fav_bool, count, text, agentid, userid){

	// html要素の取得
	const fav = document.getElementsByClassName("fav_" + id);
	const favCount = document.getElementsByClassName("favCount_" + id);
	userid_js = userid;

	// objectにbool情報を追加
	if(fav_bool_js[id] === undefined){
		fav_bool_js[id] = {};
		fav_bool_js[id]["bool"] = !fav_bool;
		fav_bool_js[id]["agent"] = agentid;
		fav_bool_js[id]["text"] = text;
		fav_bool_check = !fav_bool;
	}
	else{
		fav_bool_check = !fav_bool_js[id]["bool"];
		fav_bool_js[id]["bool"] = fav_bool_check;
	}

	const heart = (fav_bool_check === true) ? "❤" : "♡";
	for(let i = 0; i < fav.length; i++){
		fav[i].textContent = heart;
	}
	count = (fav_bool_check === true) ?
			(fav_bool === true ? count : count + 1) : (fav_bool === true ? count - 1 : count);
	for(let i = 0; i < favCount.length; i++){
		favCount[i].textContent = count;
	}
}

function changeFollow(followid, follow_bool, userid){

	// html要素の取得
	const follow = document.getElementsByClassName("follow_" + followid);
	const followCount = document.getElementsByClassName("followCount_" + followid);
	const followedCount = document.getElementsByClassName("followedCount_" + userid);
	userid_js = userid;

	// objectにbool情報を追加
	var follow_bool_check = follow_bool_js[followid];
	if(follow_bool_check === undefined){
		follow_bool_js[followid] = !follow_bool;
		follow_bool_check = !follow_bool;
	}
	else{
		follow_bool_check = !follow_bool_check
		follow_bool_js[followid] = follow_bool_check;
	}

	var button;
	const btn_follow = document.getElementsByClassName("btn_follow_" + followid);
	if(follow_bool_check === true){
		for(let btn of btn_follow) {
			button = "フォロー済み";
			btn.classList.add("btn_follow_true");
			btn.classList.remove("btn_follow_false");
		}
	}
	else{
		for(let btn of btn_follow) {
			button = "フォロー";
			btn.classList.add("btn_follow_false");
			btn.classList.remove("btn_follow_true");
		}
	}

	for(let i = 0; i < follow.length; i++){
		follow[i].textContent = button;
	}

	if(followCount.length > 0){
		var followCount_val = parseInt(followCount[0].textContent);
		followCount_val = (follow_bool_check === true) ? followCount_val + 1 : followCount_val - 1;
		for(let i = 0; i < followCount.length; i++){
			followCount[i].textContent = followCount_val;
		}
	}

	if(followedCount.length > 0){
		var followedCount_val = parseInt(followedCount[0].textContent);
		followedCount_val = (follow_bool_check === true) ? followedCount_val + 1 : followedCount_val - 1;

		for(let i = 0; i < followedCount.length; i++){
			followedCount[i].textContent = followedCount_val;
		}
	}

}

window.addEventListener('beforeunload', function() {

	if(Object.keys(fav_bool_js).length + Object.keys(follow_bool_js).length) {

		var sql1 = "INSERT INTO favinfo VALUES ";
		var sql2 = "DELETE FROM favinfo WHERE ";
		var sql5 = "INSERT INTO informinfo VALUES ";
		var count1 = 0;
		var count2 = 0;
		var count5 = 0;
		var text = "";
		Object.keys(fav_bool_js).forEach(function(key) {
			if(this[key]["bool"] === true){
				sql1 += "(" + key + ", '" + userid_js + "', NOW()), ";
				count1++;
				if(this[key]["agent"] != userid_js) {
					sql5 += "(null, '" + this[key]["agent"] + "', '" + userid_js + "', 'fav', '" +
					"あなたのコメント(" + key + ")をお気に入りしました";
					text = this[key]["text"];
					if(text.length > 0){
						if(text.length >= 21){
							text = text.substring(0,20) + "…";
						}
						sql5 += "：「" + text + "」";
					}
					sql5 += "', false, NOW()), ";
					count5++;
				}
			}
			else{
				sql2 += "(id = " + key + " AND userid = '" + userid_js + "') OR ";
				count2++;
			}
		}, fav_bool_js);
		sql1 = sql1.slice(0, -2);
		sql2 = sql2.slice(0, -4);
		sql1 += " ON DUPLICATE KEY UPDATE date = VALUES(date)";
		if(count1 === 0) {
			sql1 = "";
		}
		if(count2 === 0) {
			sql2 = "";
		}

		var sql3 = "INSERT INTO followinfo VALUES ";
		var sql4 = "DELETE FROM followinfo WHERE ";
		var count3 = 0;
		var count4 = 0;
		Object.keys(follow_bool_js).forEach(function(key) {
			if(this[key] === true){
				sql3 += "('" + userid_js + "', '" + key + "', NOW()), ";
				count3++;
				if(key != userid_js){
					sql5 += "(null, '" + key + "', '" + userid_js + "', 'follow', '" +
					"あなたをフォローしました" +
					"', false, NOW()), ";
					count5++;
				}
			}
			else{
				sql4 += "(followerid = '" + userid_js + "' AND followedid = '" + key + "') OR ";
				count4++;
			}
		}, follow_bool_js);
		sql3 = sql3.slice(0, -2);
		sql4 = sql4.slice(0, -4);
		sql5 = sql5.slice(0, -2);
		sql3 += " ON DUPLICATE KEY UPDATE date = VALUES(date)";
		sql5 += " ON DUPLICATE KEY UPDATE date = VALUES(date)";
		if(count3 === 0) {
			sql3 = "";
		}
		if(count4 === 0) {
			sql4 = "";
		}
		if(count5 === 0) {
			sql5 = "";
		}

		$.ajax({
			url: "http://localhost:8080/snsweb/executeSQL",
			type: "POST",
			data: {sql1:sql1, sql2:sql2, sql3:sql3, sql4:sql4, sql5:sql5},
			error: function (response) {
				alert(response);
			}
		});
	}

}, false);

//var mysql = require("mysql");
//var client = mysql.createClient({
//	host:"localhost",
//	user:"root",
//	password:"root123",
//	database:"snsdb"
//});
//
//function changeFavOnMySQL(fav_bool_js){
//
//	var sql = "INSERT INTO favinfo VALUES ";
//	Object.keys(fav_bool_js).forEach(function(key) {
//		sql += "(" + key + ", " + userid + ", " + this[key] + "), ";
//	}, fav_bool_js);
//	sql = sql.slice(0, -2);
//	sql += " ON DUPLICATE KEY UPDATE date = NOW()";
//	console.log(sql);
//	client.query(sql, function(error, response) {
//		if(error) throw error;
//		console.log(response);
//	})
//
//}
//
//window.addEventListener('beforeunload', function() {
//	if(Object.keys(fav_bool_js).length){
//		changeFavOnMySQL(fav_bool_js);
//	}
//}, false);