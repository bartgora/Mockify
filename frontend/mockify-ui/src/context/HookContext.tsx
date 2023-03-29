/* eslint-disable react-hooks/rules-of-hooks */
import { createContext, useContext } from 'react';
import { Event } from '../api';

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
  onCreate: () => void;
}

const HookContext = createContext<IHookContext>({
  hook: {},
  onCreate: () => ({}),
});

export function HookContextProvider({ name, events, children }: React.PropsWithChildren<IProperties>) {
  const onCreate = () => {};
  const hook = {
    name: 'bartek',
    events: [],
  } as IHookState;
  return <HookContext.Provider value={{ hook, onCreate }}>{children}</HookContext.Provider>;
}

export const useHook = useContext(HookContext);
