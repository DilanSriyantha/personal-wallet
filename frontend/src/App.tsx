import './App.css';
import ContextWrapper from './components/ContextWrapper';
import AppRoutes from './routes/AppRoutes';

function App() {
  
  return(
    <>
      <ContextWrapper>
        <AppRoutes />
      </ContextWrapper>
    </>
  );
}

export default App;
