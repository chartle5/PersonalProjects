class Appointment{
    
    constructor(){
        let __patient__;
    
    this.setPatient = function(patient){
       __patient__ = patient;
    }
    this.getPatient= function(){
        return __patient__;
    }
}

    scheduleAppointment(date){

        console.log(`An appointment has been scheduled for ${this.getPatient().fullName} on ${date}`);
    }
}
class Patient{
    constructor(fn){
        
        this.fullName = fn;
    }
}
    




class Visit extends Appointment{
     
    constructor(complaint){
        super();
        let __complaint__ = complaint
    
   
    this.setComplaint = function(c){
        __complaint__ = c;
    }
    this.getComplaint = function(){
        return __complaint__;
    }
    }
    scheduleAppointment(date){
        super.scheduleAppointment(date);
        console.log(`${this.getPatient().fullName}'s appointment's main complaint has been recorded as: ${this.getComplaint()}`);
    }
}

class Procedure extends Appointment{
    constructor(i){
       super();
       this.instructions = i;
    }
    scheduleAppointment(date){
        super.scheduleAppointment(date)
        console.log(`The following pre-procedure instructions have been provided to ${this.getPatient().fullName}: ${this.instructions}`);
    }
}
const johnSmith = new Patient("John Smith")
const aliceWonder = new Patient("Alice Wonder");

const johnProcedures = new Procedure("Drink 3 glasses of water at least 1 hour before the procedure");
const aliceProcedures = new Procedure("Fast 12h prior to your appointment");

const visit1 = new Visit("Persistant headaches");
const visit2 = new Visit ("Yearly physical");

johnProcedures.setPatient(johnSmith);
visit1.setPatient(johnSmith);
johnProcedures.scheduleAppointment("Jan. 10th");
visit1.scheduleAppointment("Feb. 1st");

aliceProcedures.setPatient(aliceWonder);
visit2.setPatient(aliceWonder);
aliceProcedures.scheduleAppointment("Mar. 10th");
visit2.scheduleAppointment("Apr. 1st")
















//Do not touch
module.exports.Appointment=Appointment;
module.exports.Visit=Visit;
module.exports.Procedure=Procedure;
module.exports.Patient=Patient;
