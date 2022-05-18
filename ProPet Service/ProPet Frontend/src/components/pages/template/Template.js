import styled from 'styled-components';
import Header from '../../molecules/header/';

const Template = (props) => {
  const body = props['body']
  return (<>
    <Header />
    <div>
      {body}
    </div>
  </>);
}

export default Template;
