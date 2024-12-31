using System;
using System.Collections.Generic;
namespace Lab3App{
    public class Coin : Treasure{
        public int Value{get; set;}
        public Coin(string description, int score, int value) : base(description, score){
            Description = description;
            Value = value;
        }

        public void UpdateTotalValue(){
            Board.TotalValue += Value;
            Console.WriteLine($"Total Value is updated to {Board.TotalValue}");
        }

        public override void Display(){
            Console.WriteLine($"Coin {Description} is displayed");
        }

        public override void AddMe(List<Collectable> collected){
            base.AddMe(collected);
            UpdateTotalValue();
        }
    }
}