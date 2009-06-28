package 
{
	/*
		Custom renderer for displaying tooltips on column header
	*/
    import mx.controls.Text;
    

    public class columnTooltip extends Text
    {
        public function columnTooltip() {
            height = 20;
        }

        override public function set data(value:Object):void {
            super.data = value;
               
            switch (this.text) {  
                       case "Name" :
                            this.toolTip = "Name of the schema";
                           break;
                       case "Score" :
                            this.toolTip = "Match score of the search result";
                           break;
                       case "Matches" :
                            this.toolTip = "Number of matching schema elements";
                           break;
                       case "E/A" :
                       		this.toolTip = "Entities / Attributes";
                       		break;
                       	case "Description" :
                   		    this.toolTip = "Description of the matching schema";
                   		    break;
                       default :
                          break;
            }
           
            super.invalidateDisplayList();
        }
    }
}
