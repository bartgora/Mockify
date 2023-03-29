import { useParams } from 'react-router-dom';

export const HookView = () => {
  const { name } = useParams();
  return <>{name}</>;
};
