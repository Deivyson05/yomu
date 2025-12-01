import { TrendingUp, Route } from "lucide-react";

interface ActionButtonsProps {
    onProgressClick: () => void;
    onTrilhaClick: () => void;
}

export function ActionButtons({ onProgressClick, onTrilhaClick }: ActionButtonsProps) {
    return (
        <div className="pb-24 md:pb-8 mt-6">
            <div className="max-w-4xl mx-auto flex flex-col gap-4">
           

                {/* Botão Trilha */}
                <button
                    onClick={onTrilhaClick}
                    className="w-full bg-green-600 text-white py-4 px-6 rounded-xl font-bold text-lg shadow-lg hover:bg-green-700 transition-all duration-300 active:scale-95 md:hover:scale-[1.02] flex items-center justify-center gap-2"
                >
                    <Route size={24} />
                    Trilha
                </button>
            </div>
        </div>
    );
}
