// in target mxml file:
// add a debug panel:
//   <de:DebugPanel id="debugPanel" width="100%" height="0%"/>
// define a debug function in <mx:Script><![CDDATA[...]]></mx:Script>
//   public function debug(str:String):void { application.debugPanel.addMessage(new DebugMessage(str)); }
package schemr {
	public class DebugMessage {
		[Bindable]
		public var time:Date;
		[Bindable]
		public var message:String;
		
		public function DebugMessage(message:String) {
			time = new Date();
			this.message = "[" + time.getMinutes()+":"+time.getSeconds() + "] " + message;
			trace(message);
		}
		
		public function toString():String {
			return message; 
		}
	}
}