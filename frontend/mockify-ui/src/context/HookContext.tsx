import { createContext, useContext, useState } from "react";
import { Event } from "../api";

export interface IProperties {
  name?: string;
  events?: Event[];
}

interface IHookState {
  name?: string;
  events?: Event[];
}

interface IHookContext {
  hook: IHookState;
  onCreate: () => {};
}

const HookContext = createContext<IHookContext>({
  hook: {},
  onCreate: () => ({}),
});

export function HookContextProvider({
  name,
  events,
  children,
}: React.PropsWithChildren<IProperties>) {
  const [hookName, setName] = useState();
  function onCreate() {}
  const hook = {
    name: "bartek",
    events: [],
  } as IHookState;
  return (
    <HookContext.Provider value={{ hook, onCreate }}>
      {children}
    </HookContext.Provider>
  );
}

export const HookStateContext = useContext(HookContext);
