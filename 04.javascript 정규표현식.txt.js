정규표현식

/패턴/ - > 정규표현식 객체

	test(문자열) : 패턴이 일치하는지 체크
	exec(문자열) : 패턴에 일치하는 문자열 추출
				: 커서 이동하면서 다음 패턴의 문자열 추출
				: 더이상 찾을 패턴 없으면 null 반환
	
	
	플래그
	/패턴/i  	-> 대소문자 구분 안함 (CASE_INSENSITIVE)
	/패턴/m  	-> 여러줄에 걸쳐 체크( MULTILINE)
	/패턴/g  	-> global : 전역에 걸쳐 패턴 체크
	
	/패턴/igm  	-> 플래그 조합 가능
	
	
	
	<a href=".." class=".." ..>
	<A HREF="
	
	
	
	const pattern = /<a/;
	console.dir(pattern);
	
	const pattern = const pattern = /<a.*href=['"]?([^'"\s]+)['"]?[^>]*?>/ig;
	pattern.exec(html);
	
	네이버 뉴스 페이지에서
	
	const el = document.querySelector(".cjs_channel_card");
	const pattern = /<img.*loading=['"]lazy['"].*src=['"]([^'"]+)['"][^>]*>/ig;
	
	const html = el.innerHTML;
	pattern.test(html);
	
	pattern.exec(html);
	pattern.exec(html);
	...