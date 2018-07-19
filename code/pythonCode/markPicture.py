from PIL import Image, ImageDraw, Image
import sys
import os

def markPicture(filepath, points):
    try:
        img = Image.open(filepath)
    except FileNotFoundError:
        return "FileNotFound"

    (tmpfilepath, filename) = os.path.split(filepath)
    
    if (len(points) % 3 != 0):
        return "pointsNumberWrong"
    
    draw = ImageDraw.Draw(img)
    
    for i in range((int)(len(points)/3)):
        left = points[3*i]
        top = points[3*i+1]
        width = points[3*i+2]
        draw.rectangle((left, top, left+width, top+width),None, (0,50,255))
    #img.show()

    try:
        img.save(tmpfilepath+"/marked/"+filename[:-(len(img.format)+1)]+"-marked."+img.format)
    except FileNotFoundError:
        os.mkdir(tmpfilepath+"/marked")
        img.save(tmpfilepath+"/marked/"+filename[:-(len(img.format)+1)]+"-marked."+img.format)

    return "success"

if __name__ == '__main__':
    #filepath = "E:/SummerProject/photo/4.jpg"
    #points = [247,115,81,44,134,76,132,180,69] 247 115 81 44 134 76 132 180 69

    filepath = sys.argv[1]
    points = []
    for i in range(2, len(sys.argv)):
        points.append((int)(sys.argv[i]))

    print(markPicture(filepath, points))




