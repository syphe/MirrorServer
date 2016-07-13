import socket

print("Creating Socket")
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect(("192.168.20.25", 9011))
print("Socket Connected")

chunks = []
bytes_recd = 0
MSGLEN = 1024
while bytes_recd < MSGLEN:
	chunk = s.recv(1024)
	print(len(chunk))
	if chunk == '':
		raise RuntimeError("socket connection broken")
	chunks.append(chunk)
	bytes_recd = bytes_recd + len(chunk)