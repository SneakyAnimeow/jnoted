export interface UserDto{
  name: string;
  email: string;
  joinDate: Date;
}

export interface NoteDto{
  id: number;
  name: string;
  content: string;
}

export interface CategoryDto{
  id: number;
  name: string;
}

export interface TokenDto {
  token: string;
}
