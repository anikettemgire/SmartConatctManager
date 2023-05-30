console.log("sdas");
const toggleSlidebar= () => {
	if($('.side').is(":visible")){
		$(".side").css("display","none");
		$(".content").css("margin-left","0");
		
	}else{
		$(".side").css("display","block");
		$(".content").css("margin-left","20%");
	}
};
const search=()=>{
	let query=$("#se-input").val();
	
	if(query==''){
        $(".search-result").hide();
	}else{
		//console.log(query);
		//sendig req
		let url=`http://localhost:8080/serach/${query}`
		fetch(url).then((response)=>{
             return response.json();
		}).then(data=>{
			//console.log(data);
			let text=`<div class='list-group'>`;
			data.forEach((contact) => {

				text+=`<a href='/user/${contact.cid}/contact' class='list-group-item list-group-action'>${contact.cname}</a>`
				
			});
             $(".search-result").html(text);
			 $(".search-result").show();
			text+=`</div>`;
		});
		$(".search-result").show();
	}
}


//first request to the servce to create order

const paymentStart=()=>{
    console.log("stared");
	let amout=$("#pymentfield").val();
	console.log(amout);
	//amout jar empty kiva null asel tar alert yenar ani retur manje tynatar kaich nai honar
	if(amout=='' || amout==null){
		//alert("amout is requried");
		swal("Something Wrong!", "Please Enter Amout!", "error");
		return ;
	}
	//will use ajax to creta order to server by using jqury version min.js 


	$.ajax(
		{
        //create url to sent the request to the server with data
		url:'/user/create_order',
		// ha data ahe apla
		data:JSON.stringify({amout:amout,info:'order_request'}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response){
			//this function invoked succes 
			console.log(response);
			//check if Resonse Apn handle Mahuds Return kel aahe tho Ala Ka Manun
			if(response.status=='created'){
				//asel tar apn form Open Karun Shakto
				//open Payment Form
				//apn Phildya js cha adi ek Rojpay chi CdN Lavaychi
				//option Strore Karnastahi
				let options={
					key:'rzp_test_0YowFv1OI7umEn',
					amout:response.amount,
					currency :'INR',
					name:'Smart Contact Manger',
					description:'Donation',
					image:'',
					order_id:response.id,
					handler:function(response){
						console.log(response.razorpay_payment_id)
						console.log(response.razorpay_order_id)
						console.log(response.razorpay_signature)
						console.log("payment succesful!!")
						//alert("congarts !!payment syyyccs")
						//phileda Update Stus KArycher Palaya maun Function
						//pass To The Function Here Will Khali Apn Function Kele Ahe
						updatePayementServer(response.razorpay_payment_id,response.razorpay_order_id,"paid");
						
					},
					prefill:{
						name:"",
						email:"",
						contact:"",

					},
                   notes:{
					address:"Aniket Temgire"
				   },
				   theme:{
					color:"#3399cc",
				   },
				};
				let rzp=new Razorpay(options);
                 rzp.on("payment.failed",function(response){
					console(response.error.code);
					console(response.error.description);
					console(response.error.source);
					console(response.error.step);
					console(response.error.reason);
					console(response.error.metadata.order_id);
					console(response.error.payment_id);
					//alert("Payment Fail");
					swal("Failed!", "Payment Failed Due To Some Resone!", "error");

				 });

				rzp.open();
			}
		},
		error:function(error){
			//invoked when error
			console.log(error);
			
		},

	});

};


function updatePayementServer(payment_id,order_id,status)
{
	$.ajax({
        //create url to sent the request to the server with data
		url:"/user/update_order",
		// ha data ahe apla
		data:JSON.stringify({payment_id:payment_id,order_id:order_id,status:status}),
		contentType:'application/json',
		type:'post',
		dataType:'json',
		//after succes
		success:function(response)
		{

			swal("Good job!", "Payment Successful!", "success");

		},
		error:function(error){
			swal("Failed!", "Payment SuccesFul But We Did Not Capture Contact As Sooon As!", "error");
		},
	});
	

}