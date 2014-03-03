# Console colors
W  = '\033[0m'  # white (normal)
R  = '\033[31m' # red
G  = '\033[32m' # green
O  = '\033[33m' # orange
B  = '\033[34m' # blue
P  = '\033[35m' # purple
C  = '\033[36m' # cyan
GR = '\033[37m' # gray
T  = '\033[93m' # tan

def getColor(color):
    if color == 'W':
        return W
    elif color == 'R':
        return R
    elif color == 'G':
        return G
    elif color == 'O':
        return O
    elif color == 'B':
        return B
    elif color == 'P':
        return P
    elif color == 'C':
        return C
    elif color == 'GR':
        return GR
    elif color == 'T':
        return T
    else:
        return W

