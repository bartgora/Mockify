import { createContext, useContext } from "react";
import { Event } from "../api";
export interface HookState {
  name?: string;
  events?: Event[];
}

interface HookContext{
    state: HookState,
    onCreate: (hookName: String) => void, 
}

const hookContext = createContext<HookContext>({
    state: {},
    onCreate: ()=> ({})
});



