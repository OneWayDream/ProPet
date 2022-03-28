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
	padding: 5% 15%;
`);


const Header = (props) => {
	const { menuProps, menuItemProps } = props;
	const border = '3px solid green'
	const height = '100px'
	const border_radius = '100px'
	return (
		<StyledHeader>
			<StyledMenu>
				<a href='/'><Image image={logo} height={height} border={border} border_radius={border_radius}/></a>
				<MenuItem href='/' menuItemProps={menuItemProps}>Главная</MenuItem>
				<MenuItem href='/search' menuItemProps={menuItemProps}>Поиск зооняни</MenuItem>
				<MenuItem href='/about' menuItemProps={menuItemProps}>О сервисе</MenuItem>
				<MenuItem href='/contacts' menuItemProps={menuItemProps}>Контакты</MenuItem>
				<Image image={profile_logo} height={height} border={border} border_radius={border_radius}/>
			</StyledMenu>
		</StyledHeader>
	);
}

export default Header;

