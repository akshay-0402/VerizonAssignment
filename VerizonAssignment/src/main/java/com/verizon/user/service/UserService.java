package com.verizon.user.service;

import static com.verizon.user.util.UserUtil.convertJsonString;
import static com.verizon.user.util.UserUtil.convertToJson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.verizon.user.entity.User;
import com.verizon.user.entity.Users;

@Service
public class UserService {
	
	private static final String USER_FILE_NAME = "userdetails.txt";
	
	private static final String USER_TEMP_FILE_NAME = "userdetails_temp.txt";

	public User getUserDetails(String userId) {
		User user = null;
		File file = new File(USER_FILE_NAME);
		BufferedReader reader = null;
		if (file.exists()) {
			try {
				reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				while (null != line) {
					user = convertJsonString(line, User.class);
					if (userId == user.getId()) {
						break;
					} else {
						line = reader.readLine();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return user;
	}

	public Users getAllUsers() {
		Users users = new Users();
		users.setUsers(Collections.emptyList());
		File file = new File(USER_FILE_NAME);
		BufferedReader reader = null;
		if (file.exists()) {
			try {
				reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				List<User> allUsers = new ArrayList<>();
				while (null != line) {
					User user = convertJsonString(line, User.class);
					allUsers.add(user);
					line = reader.readLine();
				}
				users.setUsers(allUsers);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return users;
	}

	public boolean deleteUser(String userId) {
		boolean result = false;
		File tempFile = new File(USER_TEMP_FILE_NAME);
		File file = new File(USER_FILE_NAME);
		BufferedReader reader = null;
		BufferedWriter writer = null;
		if (file.exists()) {
			try {
				reader = new BufferedReader(new FileReader(file));
				writer = new BufferedWriter(new FileWriter(tempFile));
				String readLine = reader.readLine();
				while (null != readLine) {
					User user = convertJsonString(readLine, User.class);
					if (user.getId().equalsIgnoreCase(userId)) {
						result = true;
					} else {
						writer.write(readLine + System.lineSeparator());
					}
					readLine = reader.readLine();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					writer.close();
					reader.close();
					System.out.println("File Deleted ?" + file.delete());
					System.out.println("File renamed ? "+tempFile.renameTo(file));;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public User saveUserDetails(User userDetails) {
		File file = new File(USER_FILE_NAME);
		FileWriter fileWriter = null;
		try {
			if (file.createNewFile()) {
				System.out.println("file created");
			} else {
				System.out.println("file already exist");
			}
			fileWriter = new FileWriter(file, true);
			userDetails.setId(UUID.randomUUID().toString());
			String convertToJson = convertToJson(userDetails);
			String result = convertToJson + System.lineSeparator();
			fileWriter.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fileWriter) {
				try {
					fileWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return userDetails;
	}
}
