import styled from 'styled-components';
import MenuItem from '../../atoms/menuItem/';
import Image from '../../atoms/image/';
import logo from '../../../img/logo.png'
import profile_logo from '../../../img/profile_logo.png'

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
				<a href='/'><Image image={logo} height={height} border={border} border_radius={border_radius}/></a>
				<MenuItem href='/'>Главная</MenuItem>
				<MenuItem href='/search'>Поиск зооняни</MenuItem>
				<MenuItem href='/about'>О сервисе</MenuItem>
				<MenuItem href='/contacts'>Контакты</MenuItem>
				<a href='/login'><Image image={profile_logo} height={height} border={border} border_radius={border_radius}/></a>
			</StyledMenu>
		</StyledHeader>
	);
}

export default Header;

