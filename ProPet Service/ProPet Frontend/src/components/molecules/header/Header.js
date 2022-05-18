import styled from 'styled-components';
import MenuItem from '../../atoms/menuItem/';
import Image from '../../atoms/image/';
import logo from '../../../img/logo.png'
import profile_logo from '../../../img/profile_logo.png'
import { Link } from 'react-router-dom';
import paths from '../../../configs/paths';

const StyledMenu = styled.div((props) => `
	color: black;
	display: flex;
	justify-content: space-between;
	align-items: center;
`);

const StyledHeader = styled.div(() => `
  font-size: 1.5vw;
	padding-bottom: 7vw;
`);


const Header = () => {
	const border = '3px solid green'
	const height = '6vw'
	const border_radius = '100px'
	return (
		<StyledHeader>
			<StyledMenu>
				<Link to={paths.DEFAULT}><Image image={logo} height={height} border={border} border_radius={border_radius} /></Link>
				<MenuItem href={paths.DEFAULT}>Главная</MenuItem>
				<MenuItem href={paths.SEARCH}>Поиск зооняни</MenuItem>
				<MenuItem href={paths.ABOUT}>О сервисе</MenuItem>
				<MenuItem href={paths.CONTACTS}>Контакты</MenuItem>
				<Link to={paths.SIGN_IN}><Image image={profile_logo} height={height} border={border} border_radius={border_radius} /></Link>
			</StyledMenu>
		</StyledHeader>
	);
}

export default Header;
