angular.module('scheduleApp', ['angular-advanced-searchbox'])
.filter('offset', function() {
  return function(input, start) {
    start = parseInt(start, 10);
    return input.slice(start);
  };
})

.controller('mainController', function($scope, $http) {
  
  $scope.showDoctorSearchSection = true;
  $scope.showDoctorsListSection = false;
  $scope.showBookAppointmentSection = false;
  $scope.availableSearchParams = [
    { key: "name", name: "Name", placeholder: "Name..." },
    { key: "locality", name: "Locality", placeholder: "Locality..." },
    { key: "speciality", name: "Speciality", placeholder: "Speciality..." },
    { key: "city", name: "City", placeholder: "City..." },
    // { key: "emailAddress", name: "E-Mail", placeholder: "E-Mail...", allowMultiple: true },
    // { key: "job", name: "Job", placeholder: "Job..." }
  ];  
  $scope.doctorList = [
    {

      "docName": "Rajesh Rathi",

      "degree": "MBBS, MD",

      "address1": "Congress Nagar",

      "address2": "Dhantoli",

      "city": "Nagpur",

      "state": "Maharashtra",

      "zip": "440001",

      "specialization": "Psychiatrist, Addiction Psychiatrist, Geriatric Psychiatrist",

      "cellphone": "9890034523",

      "email": "sumit_pathak@gmail.com",

      "fees": "300.0",

      "aboutDoctor": "Hello. i am Dr Rajesh Rathi. I have done my M.D. in Psychiatry from wardha for which I received a Gold Medal at the hands of hon'ble Shri Sharad Pawar. After that, I was selected as a senior resident in the prestigious Neuropsychiatry institute of North India which is IHBAS in Delhi where I worked for 3 years which was a life changing and enriching experience. I especially worked in the fields of Drug abuse (Deaddiction), Child Psychiatry and Forensic Psychiatry at IHBAS. I also gained great experience at IHBAS in Psychotherapy or counselling related to various issues like Interpersonal problems, Marital conflicts, Famliy problems, Job or studies related stressors, career guidance, relationship problems, parental counselling for child related problems etc. I also gained my interest in treating patients with sexual problems because of the Marital and Psychosexual clinic functioning at IHBAS. Owing to working under a multidisciplinary team, I believe the holistic management of any problem is necessary and thats what I strive to do.",

      "doctorID": "1"

  },
  {

    "docName": "Rajesh Rathi",

    "degree": "MBBS, MD",

    "address1": "Congress Nagar",

    "address2": "Dhantoli",

    "city": "Nagpur",

    "state": "Maharashtra",

    "zip": "440001",

    "specialization": "Psychiatrist, Addiction Psychiatrist, Geriatric Psychiatrist",

    "cellphone": "9890034523",

    "email": "sumit_pathak@gmail.com",

    "fees": "300.0",

    "aboutDoctor": "Hello. i am Dr Rajesh Rathi. I have done my M.D. in Psychiatry from wardha for which I received a Gold Medal at the hands of hon'ble Shri Sharad Pawar. After that, I was selected as a senior resident in the prestigious Neuropsychiatry institute of North India which is IHBAS in Delhi where I worked for 3 years which was a life changing and enriching experience. I especially worked in the fields of Drug abuse (Deaddiction), Child Psychiatry and Forensic Psychiatry at IHBAS. I also gained great experience at IHBAS in Psychotherapy or counselling related to various issues like Interpersonal problems, Marital conflicts, Famliy problems, Job or studies related stressors, career guidance, relationship problems, parental counselling for child related problems etc. I also gained my interest in treating patients with sexual problems because of the Marital and Psychosexual clinic functioning at IHBAS. Owing to working under a multidisciplinary team, I believe the holistic management of any problem is necessary and thats what I strive to do.",

    "doctorID": "1"

},{

  "docName": "Rajesh Rathi",

  "degree": "MBBS, MD",

  "address1": "Congress Nagar",

  "address2": "Dhantoli",

  "city": "Nagpur",

  "state": "Maharashtra",

  "zip": "440001",

  "specialization": "Psychiatrist, Addiction Psychiatrist, Geriatric Psychiatrist",

  "cellphone": "9890034523",

  "email": "sumit_pathak@gmail.com",

  "fees": "300.0",

  "aboutDoctor": "Hello. i am Dr Rajesh Rathi. I have done my M.D. in Psychiatry from wardha for which I received a Gold Medal at the hands of hon'ble Shri Sharad Pawar. After that, I was selected as a senior resident in the prestigious Neuropsychiatry institute of North India which is IHBAS in Delhi where I worked for 3 years which was a life changing and enriching experience. I especially worked in the fields of Drug abuse (Deaddiction), Child Psychiatry and Forensic Psychiatry at IHBAS. I also gained great experience at IHBAS in Psychotherapy or counselling related to various issues like Interpersonal problems, Marital conflicts, Famliy problems, Job or studies related stressors, career guidance, relationship problems, parental counselling for child related problems etc. I also gained my interest in treating patients with sexual problems because of the Marital and Psychosexual clinic functioning at IHBAS. Owing to working under a multidisciplinary team, I believe the holistic management of any problem is necessary and thats what I strive to do.",

  "doctorID": "1"

}
  ]
  $scope.searchDoctors = function(){
    // $("#loadMe").modal({
    //   backdrop: "static", //remove ability to close modal with click
    //   keyboard: false, //remove option to close with keyboard
    //   show: true //Display loader!
    // });
    // setTimeout(function() {
    //   $("#loadMe").modal("hide");
    // }, 3500);
  
  
    console.log($scope.searchParams);
    var isFreeText = true;
    var data = {};
    if($scope.searchParams.name){
      isFreeText=false;
      data.docName = $scope.searchParams.name;
    }
    if($scope.searchParams.locality){
      isFreeText=false;
      data.address1 = $scope.searchParams.locality;
    }
    if($scope.searchParams.speciality){
      isFreeText=false;
      data.specialization = $scope.searchParams.speciality;
    }
    if($scope.searchParams.city){
      isFreeText=false;
      data.city = $scope.searchParams.city;
    }
    if(isFreeText && $scope.searchParams.query){
      data.docName = $scope.searchParams.query;
    }

    var url = "http://localhost:8080/appointment/searchDoctors";

    $http.post(url, data).then(function(result){
      console.log(result);
      $scope.doctorList = result.data;
      $scope.showDoctorsListSection = true;
      $scope.showDoctorSearchSection = false;
      }, function(err){
        $scope.showDoctorsListSection = true;
        $scope.showDoctorSearchSection = false;
      });

  };

  $scope.selectedSlotDetails ={};
  $scope.changeSlot = function (day, slot, dayIndex, slotIndex) {
console.log(day, slot, dayIndex, slotIndex);
if($scope.selectedSlotDetails.day){
  $scope.appointmentDetails[$scope.selectedSlotDetails.dayIndex].slots[$scope.selectedSlotDetails.slotIndex].booked= false;;
}


$scope.selectedSlotDetails= {
  day, slot, dayIndex, slotIndex
};
console.log($scope.selectedSlotDetails);
  }
  $scope.checkAvailability = function (doctordetails) {
    $scope.selectedDoctor = doctordetails;
    console.log(doctordetails);
    var docId = doctordetails.doctorID;
    var appointmentDate= new Date(doctordetails.appointmentDate);
    appointmentDate =moment(appointmentDate).format("YYYY-MM-DD");
    console.log(appointmentDate);

    var url = "http://localhost:8080/appointment/getSlots";
    var data={
     "docID":docId,
      "date":appointmentDate
    }
    $http.post(url, data).then(function(result){
      console.log(result.data);
      var appointmentDetailsTemp =result.data;
      // $scope.appointmentDetails.map(appointmentDet => {

      // })
      var appointArray =[];      
      for(var g=0; g<appointmentDetailsTemp.length; g++){
        var dateT= new Date(appointmentDetailsTemp[g].date);
        var weekdayTemp= moment(dateT).format('dddd');
        var dateTemp = moment(dateT).format("YYYY-MM-DD");
        var slotStartDate = new Date(appointmentDetailsTemp[g].startTime);
        var slotStart =  moment(slotStartDate).format('hh:mm A');
        var slotEndDate = new Date(appointmentDetailsTemp[g].endTime);
        var slotEnd =  moment(slotEndDate).format('hh:mm A');

        var slotObj = {
          "slotStart":slotStart,
          "slotEnd":slotEnd,
          "booked":false,
          "slotDetails":appointmentDetailsTemp[g]
        };
        var slotFound = false;
        for(var d=0; d< appointArray.length;d++){
          if(appointArray[d].weekDate == dateTemp){
            appointArray[d].slots.push(slotObj);
            slotFound = true;
          }
        }
        if(!slotFound){
          appointArray.push({
            "weekday":weekdayTemp,
            "weekDate":dateTemp,
            "slots":[slotObj]
          })
        }
      }
      console.log(appointArray);
      $scope.appointmentDetails = angular.copy(appointArray);
      $scope.showDoctorsListSection = false;
      $scope.showBookAppointmentSection = true;
  
      }, function(err){
        bootbox.alert("Not able fetch Slots. Please try again!")
      });

  };
$scope.patientDetails = {
  "firstname":"",
  "lastName":"",
  "email":"",
  "cellPhone":"",
  "address":"",
  "docId":"",
  "slotId":"",

};
$scope.cancelAppointment = function () {
  $scope.appointmentID;
  
  var url = "http://localhost:8080/appointment/cancelBooking?bookingId="+$scope.appointmentID;
  $http.delete(url).then(function(result){
    console.log(result);
    if(result.data){
      bootbox.alert("Booking cancelled successfully!");
      $("#cancelAppointment").modal('hide');
    }else{
      bootbox.alert("Invalid Appointment ID provided!!")
      $("#cancelAppointment").modal('hide');
    }
    }, function(err){
      bootbox.alert("Booking cancellation failed!!")
      $("#cancelAppointment").modal('hide');
    });
}
$scope.bookAppointment = function () {
  var data = $scope.patientDetails;
  data.docId= Number($scope.selectedDoctor.doctorID);
  data.slotId= Number($scope.selectedSlotDetails.slot.slotDetails.slotID);
console.log(data);
var url = "http://localhost:8080/appointment/bookSlot";

    $http.post(url, data).then(function(result){
      console.log(result);
      if(angular.isNumber(result.data)){
        $scope.showDoctorSearchSection = true;
        $scope.showDoctorsListSection = false;
        $scope.showBookAppointmentSection = false;
        $("#myModal").modal('hide');

        bootbox.alert("Slot booking has been confirmed. Booking ID : "+result.data);
      }else{
        bootbox.alert("Booking failed!!")
      }
      }, function(err){
        if(err.message.includes("ExceptionDuplicateSlot")){
          bootbox.alert("This appointment is not available. Please try again with another slot!!")
        }else{
          bootbox.alert("Booking failed!!")
        }
      });

}


  // $scope.appointmentDetails = [
  //   {
  //     name: 'Monday',
  //     slots: [
  //       {
  //         time: '9:00am',
  //         booked: false
  //       },
  //        {
  //         time: '11:00am',
  //         booked: false
  //       },
  //        {
  //         time: '1:00pm',
  //         booked: false
  //       },
  //        {
  //         time: '3:00pm',
  //         booked: false
  //       },
  //        {
  //         time: '5:00pm',
  //         booked: false
  //       },
  //       {
  //         time: '7:00pm',
  //         booked: false
  //       }
  //     ]
  //   },
  //    {
  //     name: 'Tuesday',
  //     slots: [
  //        {
  //         time: '9:00am',
  //         booked: false
  //       },
  //        {
  //         time: '11:00am',
  //         booked: false
  //       },
  //       {
  //         time: '1:00pm',
  //         booked: false
  //       },
  //        {
  //         time: '3:00pm',
  //         booked: false
  //       },
  //       {
  //         time: '5:00pm',
  //         booked: false
  //       },
  //        {
  //         time: '7:00pm',
  //         booked: false
  //       }
  //     ]
  //   },
  //   {
  //     name: 'Wednesday',
  //     slots: [
  //        {
  //         time: '9:00am',
  //         booked: false
  //       },
  //        {
  //         time: '11:00am',
  //         booked: false
  //       },
  //        {
  //         time: '1:00pm',
  //         booked: false
  //       },
  //        {
  //         time: '3:00pm',
  //         booked: false
  //       },
  //       {
  //         time: '5:00pm',
  //         booked: false
  //       },
  //        {
  //         time: '7:00pm',
  //         booked: false
  //       }
  //     ]
  //   }
  // ]


});
