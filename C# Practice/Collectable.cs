using System;
namespace Lab3App{
    public abstract class Collectable : Displayable{
        public CollectionBoard Board {get; set;}
        public string Description;

        public Collectable(string description){
            this.Description = description;
        }

        public virtual void AddMe(List<Collectable> collected){
            Console.WriteLine($"{Description} Collected, Congrats!!!!");
            collected.Add(this);
        }

        public abstract void Display();
    }
}