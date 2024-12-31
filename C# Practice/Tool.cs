using System;
using System.Collections.Generic;

namespace Lab3App{
    
    public abstract class Tool : Collectable{

        public Tool(string description) : base(description){}
        public virtual void DoAction(){}

        public override void AddMe(List<Collectable> collected){
            base.AddMe(collected);
            DoAction();
        }
    }

}