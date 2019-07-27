$(function(){
	//登录验证的controller url
	var loginUrl="/o2o/local/logincheck";
	//从地址栏的url获取usertype，为1则为顾客，为2则为商家
	var usertype=getQueryString("usertype");
	//登录次数，累计三次失败后弹出验证码
	var loginCount=0;
	$("#submit").click(function(){
		//获取账号
		var userName=$("#username").val();
		//获取密码
		var password=$("#psw").val();
		//获取验证码信息
		var verifyCodeActual=$("#j_captcha").val();
		//是否需要验证码，默认为false
		var needVerify=false;
		//如果三次都登录失败
		if(loginCount>=3){
			//那么就需要验证码校验了
			if(!verifyCodeActual){
				$.toast("请输入验证码");
				return;
			}
			else{
				needVerify=true;
			}
		}
		//访问后进行登录验证
		$.ajax({
			url:loginUrl,
			async:false,
			cache:false,
			type:"post",
			dataType:"json",
			data:{
				userName:userName,
				password:password,
				verifyCodeActual:verifyCodeActual,
				//是否需要验证
				needVerify:needVerify
			},
		success:function(data){
			if(data.success){
				$.toast("登录成功");
				if(usertype==1){
					window.location.href="/o2o/frontend/index";
				}
				else{
					window.location.href="/o2o/shopadmin/shoplist";
					return false;
				}
			}
			else{
				$.toast("登录失败:"+data.errMsg);
				loginCount++;
				if(loginCount>=3){
					//三次登录失败，展示验证码框
					$("#verifyPart").show();
				}
				
			}
		}
		})
		
	})
	
	
})