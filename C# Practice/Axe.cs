using System;

namespace Lab3App{
    public class Axe : Tool{
        public Axe(string description) : base(description){}

        public override void DoAction(){
            base.DoAction();
            Console.WriteLine("Axe is used");
        }

        public override void Display(){
            console.WriteLine($"Axe {Description} is displayed")
        }
    }
}