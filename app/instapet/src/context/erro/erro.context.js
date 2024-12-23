import createGlobalState from 'react-create-global-state';

const initialState = false;

const [useGlobalErro, Provider] = createGlobalState(initialState);

export const GlobalErroProvider = Provider;

export default useGlobalErro;
